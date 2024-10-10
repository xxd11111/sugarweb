package com.sugarweb.chatAssistant.temp;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.application.ChatMemoryService;
import com.sugarweb.chatAssistant.application.PromptService;
import com.sugarweb.chatAssistant.domain.po.ChatMessageInfo;
import com.sugarweb.chatAssistant.domain.po.PromptTemplateInfo;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * RagPipeline
 *
 * @author xxd
 * @version 1.0
 */
@Component
public class MultiuserRagPipeline {
    private PromptService promptService = new PromptService();
    @Resource
    private StreamingChatLanguageModel chatLanguageModel;
    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    private SystemMessage getSystemMessage(String agentId, Map<String, String> contextVariables) {
        PromptTemplateInfo promptTemplateInfo = promptService.getSystemPrompt(agentId);
        String templateContent = promptTemplateInfo.getTemplateContent();

        PromptTemplate systemPromptTemplate = new PromptTemplate(templateContent);
        Prompt systemPrompt = systemPromptTemplate.apply(contextVariables);
        return new SystemMessage(systemPrompt.text());
    }

    private UserMessage getUserMessage(String agentId, Map<String, String> contextVariables) {
        PromptTemplateInfo promptTemplateInfo = promptService.getUserPrompt(agentId);
        PromptTemplate userPromptTemplate = new PromptTemplate(promptTemplateInfo.getTemplateContent());
        Prompt userPrompt = userPromptTemplate.apply(contextVariables);
        return new UserMessage(userPrompt.text());
    }

    @Transactional(rollbackFor = Exception.class)
    public void chat(List<BaseMsg> msgList, Consumer<String> tokenConsumer, Consumer<Response<AiMessage>> responseConsumer) {
        String agentId = "1";
        String memoryId = "1";

        Map<String, String> contextVariables = new HashMap<>();
        StringBuilder questionSb = new StringBuilder();
        for (BaseMsg baseMsg : msgList) {
            questionSb.append(baseMsg.getContent()).append("\n");
        }
        contextVariables.put("question", questionSb.toString());

        StringBuilder retrievalSegmentSb = new StringBuilder();
        for (BaseMsg baseMsg : msgList) {
            //获取相关召回文档
            retrievalSegmentSb.append(getRetrievalSegment(baseMsg.getContent())).append("\n");
        }
        contextVariables.put("documents", retrievalSegmentSb.toString());

        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        SystemMessage systemMessage = getSystemMessage(agentId, contextVariables);
        messageList.add(systemMessage);

        //第二步，获取ai对话历史消息
        ChatMemoryService chatMemoryService = new ChatMemoryService();
        List<ChatMessageInfo> chatMemoryInfos = chatMemoryService.listChatMessage(memoryId, 10);
        //组装历史消息
        for (ChatMessageInfo chatMessageInfo : chatMemoryInfos) {
            ChatMessage chatMessage = getChatMessage(chatMessageInfo);
            messageList.add(chatMessage);
        }
        //第三步，配置当前提问的消息
        UserMessage userMessage = getUserMessage(agentId, contextVariables);
        messageList.add(userMessage);

        //获取生成结果
        StreamingResponseHandler<AiMessage> baseHandler = new StreamingResponseHandler<>() {
            @Override
            public void onComplete(Response<AiMessage> response) {
                StreamingResponseHandler.super.onComplete(response);
                AiMessage aiMessage = response.content();
                String aiMessageText = aiMessage.text();
                //保存对话消息
                ChatMessageInfo userChatMessageInfo = createChatMessageInfo("user", questionSb.toString(), memoryId);
                ChatMessageInfo aiChatMessageInfo = createChatMessageInfo("ai", aiMessageText, memoryId);
                List<ChatMessageInfo> batchSaveList = new ArrayList<>();
                batchSaveList.add(userChatMessageInfo);
                batchSaveList.add(aiChatMessageInfo);
                Db.saveBatch(batchSaveList);

                responseConsumer.accept(response);
            }

            @Override
            public void onNext(String token) {
                tokenConsumer.accept(token);
            }

            @Override
            public void onError(Throwable error) {
                throw new RuntimeException("大模型响应错误:" + error.getMessage(), error);
            }
        };
        chatLanguageModel.generate(messageList, baseHandler);

    }

    private ChatMessageInfo createChatMessageInfo(String type, String question, String memoryId) {
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setMemoryId(memoryId);
        chatMessageInfo.setContent(question);
        chatMessageInfo.setMessageType(type);
        chatMessageInfo.setCreateTime(LocalDateTime.now());
        chatMessageInfo.setUpdateTime(LocalDateTime.now());
        return chatMessageInfo;
    }

    private ChatMessage getChatMessage(ChatMessageInfo chatMessageInfo) {
        if ("user".equals(chatMessageInfo.getMessageType())) {
            return new UserMessage(chatMessageInfo.getContent());
        } else if ("ai".equals(chatMessageInfo.getMessageType())) {
            return new AiMessage(chatMessageInfo.getContent());
        } else if ("system".equals(chatMessageInfo.getMessageType())) {
            return new SystemMessage(chatMessageInfo.getContent());
        }
        throw new IllegalArgumentException(StrUtil.format("不支持的消息类型,messageId:{}", chatMessageInfo.getMessageId()));
    }

    /**
     * 获取召回片段
     */
    public String getRetrievalSegment(String queryMessage) {
        TextSegment textSegment = TextSegment.from(queryMessage);
        Response<Embedding> embeddingResponse = embeddingModel.embed(textSegment);
        //获取嵌入向量
        Embedding embedding = embeddingResponse.content();
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding)
                .maxResults(10)
                .minScore(0.7)
                .build();
        EmbeddingSearchResult<TextSegment> embeddingSearchResult = embeddingStore.search(embeddingSearchRequest);
        List<EmbeddingMatch<TextSegment>> embeddingMatchList = embeddingSearchResult.matches();
        StringBuilder documentStr = new StringBuilder();
        //装载文档
        for (EmbeddingMatch<TextSegment> textSegmentEmbeddingMatch : embeddingMatchList) {
            String text = textSegmentEmbeddingMatch.embedded().text();
            documentStr.append(text).append("\n");
        }
        return documentStr.toString();
    }


}
