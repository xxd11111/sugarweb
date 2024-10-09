package com.sugarweb.chatAssistant.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.po.*;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

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
    private ChatLanguageModel chatLanguageModel;
    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    private SystemMessage getSystemMessage(String agentId, Map<String, String> contextVariables){
        PromptTemplateInfo promptTemplateInfo = promptService.getSystemPrompt(agentId);
        String templateContent = promptTemplateInfo.getTemplateContent();

        PromptTemplate systemPromptTemplate = new PromptTemplate(templateContent);
        Prompt systemPrompt = systemPromptTemplate.apply(contextVariables);
        return new SystemMessage(systemPrompt.text());
    }

    private UserMessage getUserMessage(String agentId, Map<String, String> contextVariables){
        PromptTemplateInfo promptTemplateInfo = promptService.getUserPrompt(agentId);
        PromptTemplate userPromptTemplate = new PromptTemplate(promptTemplateInfo.getTemplateContent());
        Prompt userPrompt = userPromptTemplate.apply(contextVariables);
        return new UserMessage(userPrompt.text());
    }

    @Transactional(rollbackFor = Exception.class)
    public String chat(String question) {
        Map<String, String> contextVariables = new HashMap<>();
        contextVariables.put("question", question);
        //获取相关召回文档
        String retrievalSegment = getRetrievalSegment(question);
        contextVariables.put("documents", retrievalSegment);


        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        SystemMessage systemMessage = getSystemMessage("1", contextVariables);
        messageList.add(systemMessage);

        //第二步，获取用户和ai对话历史消息
        ChatMemoryService chatMemoryService = new ChatMemoryService();
        List<ChatMessageInfo> chatMemoryInfos = chatMemoryService.listChatMessage("1", 10);
        //组装历史消息
        for (ChatMessageInfo chatMessageInfo : chatMemoryInfos) {
            ChatMessage chatMessage = getChatMessage(chatMessageInfo);
            messageList.add(chatMessage);
        }
        //第三步，配置当前提问的用户消息
        UserMessage userMessage = getUserMessage("1", contextVariables);
        messageList.add(userMessage);

        //获取生成结果
        Response<AiMessage> generate = chatLanguageModel.generate(messageList);
        AiMessage aiMessage = generate.content();
        String aiMessageText = aiMessage.text();

        //保存对话消息
        ChatMessageInfo userChatMessageInfo = createChatMessageInfo("user", question, "1");
        ChatMessageInfo aiChatMessageInfo = createChatMessageInfo("ai", aiMessageText, "1");
        List<ChatMessageInfo> batchSaveList = new ArrayList<>();
        batchSaveList.add(userChatMessageInfo);
        batchSaveList.add(aiChatMessageInfo);
        Db.saveBatch(batchSaveList);

        return aiMessageText;
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
