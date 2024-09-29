package com.sugarweb.chatAssistant.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.po.BlblUser;
import com.sugarweb.chatAssistant.domain.po.ChatMemoryInfo;
import com.sugarweb.chatAssistant.domain.po.ChatMessageInfo;
import com.sugarweb.chatAssistant.domain.po.StageMemory;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * RagPipeline
 *
 * @author xxd
 * @version 1.0
 */
public class RagPipeline {

    private static PromptService promptService = new PromptService();

    private static final InMemoryEmbeddingStore<TextSegment> inMemoryEmbeddingStore = new InMemoryEmbeddingStore<>();
    private static boolean useInMemoryVectorStore = true;
    private static final ChatLanguageModel chatLanguageModel = OllamaChatModel.builder()
            .logRequests(true)
            .logResponses(true)
            .temperature(0.75)
            .listeners(List.of(new ChatModelListener() {
                @Override
                public void onResponse(ChatModelResponseContext responseContext) {
                }
            }))
            .baseUrl("http://localhost:11434")
            .modelName("qwen2.5:7b")
            .build();

    private static final EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("nomic-embed-text")
            .build();

    // private static final EmbeddingStore<TextSegment> embeddingStore = MilvusEmbeddingStore.builder()
    //         .uri("http://192.168.193.151:19530")
    //         .username("root")
    //         .password("milvus")
    //         .databaseName("default")
    //         .collectionName("vector_store4")
    //         .dimension(768)
    //         .consistencyLevel(ConsistencyLevelEnum.STRONG)
    //         // .indexType(IndexType.IVF_FLAT)
    //         .metricType(MetricType.COSINE)
    //         .build();

    public String bilibiliAiChat(String eventMessage, String sendUserId, String stageId) {

        BlblUser blblUser = Db.getById(sendUserId, BlblUser.class);
        if (blblUser == null) {
            blblUser = new BlblUser();
            blblUser.setBlblUid(sendUserId);
            blblUser.setUserName(eventMessage);
            blblUser.setCreateTime(LocalDateTime.now());
            blblUser.setUpdateTime(LocalDateTime.now());
            Db.save(blblUser);
        }

        StageMemory stageMemory = Db.getOne(new LambdaQueryWrapper<>(StageMemory.class)
                .eq(StageMemory::getStageId, stageId)
                .eq(StageMemory::getUserId, sendUserId));
        if (stageMemory == null) {

            ChatMemoryInfo chatMemoryInfo = new ChatMemoryInfo();
            chatMemoryInfo.setUserId(sendUserId);
            //标题设置为第一次的提问
            chatMemoryInfo.setTitle(eventMessage);
            chatMemoryInfo.setCreateTime(LocalDateTime.now());
            chatMemoryInfo.setUpdateTime(LocalDateTime.now());
            Db.save(chatMemoryInfo);
            String memoryId = chatMemoryInfo.getMemoryId();
            stageMemory = new StageMemory();
            stageMemory.setStageId(stageId);
            stageMemory.setMemoryId(memoryId);
            stageMemory.setUserId(sendUserId);
            Db.save(stageMemory);

            return firstChat(eventMessage, memoryId, sendUserId);
        } else {
            return chatWithMemory(eventMessage, stageMemory.getMemoryId(), sendUserId);
        }
    }

    //单用户场景
    private String chatWithMemory(String eventMessage, String memoryId, String sendUserId) {
        //获取相关召回文档
        String retrievalSegment = getRetrievalSegment(eventMessage);
        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        PromptTemplate systemPromptTemplate = new PromptTemplate(promptService.getSystemPrompt());
        HashMap<String, Object> systemVariables = new HashMap<>();
        //装载召回片段
        systemVariables.put("documents", retrievalSegment);
        Prompt systemPrompt = systemPromptTemplate.apply(systemVariables);
        SystemMessage systemMessage = new SystemMessage(systemPrompt.text());
        messageList.add(systemMessage);
        String systemMessageId = null;
        //第二步，获取用户和ai对话历史消息
        int maxMessageCount = 10;
        List<ChatMessageInfo> list = Db.lambdaQuery(ChatMessageInfo.class)
                .eq(ChatMessageInfo::getMemoryId, memoryId)
                .orderByAsc(ChatMessageInfo::getCreateTime)
                .last("limit " + (maxMessageCount + 1))
                .list();
        for (ChatMessageInfo chatMessageInfo : list) {
            ChatMessage chatMessage = getChatMessage(chatMessageInfo);
            if (!"system".equals(chatMessageInfo.getMessageType())) {
                messageList.add(chatMessage);
            } else {
                systemMessageId = chatMessageInfo.getMessageId();
            }
        }
        //第三步，配置当前提问的用户消息
        PromptTemplate userPromptTemplate = new PromptTemplate(promptService.getPrompt());
        HashMap<String, Object> userVariables = new HashMap<>();
        userVariables.put("question", eventMessage);
        Prompt userPrompt = userPromptTemplate.apply(userVariables);
        UserMessage userMessage = new UserMessage(userPrompt.text());
        messageList.add(userMessage);

        //获取生成结果
        Response<AiMessage> generate = getChatModel().generate(messageList);
        AiMessage aiMessage = generate.content();
        String aiMessageText = aiMessage.text();

        Db.lambdaUpdate(ChatMessageInfo.class)
                .set(ChatMessageInfo::getContent, systemPrompt.text())
                .set(ChatMessageInfo::getUpdateTime, LocalDateTime.now())
                .eq(ChatMessageInfo::getMessageId, systemMessageId)
                .update();
        //保存对话消息
        ChatMessageInfo userChatMessageInfo = createChatMessageInfo("user", eventMessage, memoryId, sendUserId);
        ChatMessageInfo aiChatMessageInfo = createChatMessageInfo("ai", aiMessageText, memoryId, "");
        List<ChatMessageInfo> batchSaveList = new ArrayList<>();
        batchSaveList.add(userChatMessageInfo);
        batchSaveList.add(aiChatMessageInfo);
        Db.saveBatch(batchSaveList);

        return aiMessageText;
    }

    private String firstChat(String eventMessage, String memoryId, String sendUserId) {
        //获取相关召回文档
        String retrievalSegment = getRetrievalSegment(eventMessage);
        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        PromptTemplate systemPromptTemplate = new PromptTemplate(promptService.getSystemPrompt());
        HashMap<String, Object> systemVariables = new HashMap<>();
        //装载召回片段
        systemVariables.put("documents", retrievalSegment);
        Prompt systemPrompt = systemPromptTemplate.apply(systemVariables);
        SystemMessage systemMessage = new SystemMessage(systemPrompt.text());
        messageList.add(systemMessage);
        //配置当前提问的用户消息
        PromptTemplate userPromptTemplate = new PromptTemplate(promptService.getPrompt());
        HashMap<String, Object> userVariables = new HashMap<>();
        userVariables.put("question", eventMessage);
        Prompt userPrompt = userPromptTemplate.apply(userVariables);
        UserMessage userMessage = new UserMessage(userPrompt.text());
        messageList.add(userMessage);

        //获取生成结果
        Response<AiMessage> generate = getChatModel().generate(messageList);
        AiMessage aiMessage = generate.content();
        String aiMessageText = aiMessage.text();

        //保存对话消息
        ChatMessageInfo systemChatMessageInfo = createChatMessageInfo("system", systemMessage.text(), memoryId, "");
        ChatMessageInfo userChatMessageInfo = createChatMessageInfo("user", eventMessage, memoryId, sendUserId);
        ChatMessageInfo aiChatMessageInfo = createChatMessageInfo("ai", aiMessageText, memoryId, "");
        List<ChatMessageInfo> batchSaveList = new ArrayList<>();
        batchSaveList.add(systemChatMessageInfo);
        batchSaveList.add(userChatMessageInfo);
        batchSaveList.add(aiChatMessageInfo);
        Db.saveBatch(batchSaveList);

        return aiMessageText;

    }


    private ChatMessageInfo createChatMessageInfo(String type, String content, String memoryId, String sendUserId) {
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setMemoryId(memoryId);
        chatMessageInfo.setContent(content);
        chatMessageInfo.setMessageType(type);
        chatMessageInfo.setSendUserId(sendUserId);
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
     * 获取聊天大模型
     */
    public ChatLanguageModel getChatModel() {
        return chatLanguageModel;
    }

    /**
     * 获取Embedding模型
     */
    public EmbeddingModel getEmbeddingModel() {
        return embeddingModel;
    }

    /**
     * 获取向量存储库
     */
    public EmbeddingStore<TextSegment> getEmbeddingStore() {
        return inMemoryEmbeddingStore;
        // if (useInMemoryVectorStore){
        //     return inMemoryEmbeddingStore;
        // }else {
        //     return embeddingStore;
        // }
    }

    /**
     * 获取召回片段
     */
    public String getRetrievalSegment(String queryMessage) {
        EmbeddingModel embeddingModel = getEmbeddingModel();
        TextSegment textSegment = TextSegment.from(queryMessage);
        Response<Embedding> embeddingResponse = embeddingModel.embed(textSegment);
        //获取嵌入向量
        Embedding embedding = embeddingResponse.content();
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding)
                .maxResults(10)
                .minScore(0.7)
                .build();
        EmbeddingSearchResult<TextSegment> embeddingSearchResult = getEmbeddingStore().search(embeddingSearchRequest);
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
