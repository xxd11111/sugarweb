// package com.sugarweb.chatAssistant.application;
//
// import cn.hutool.core.util.StrUtil;
// import com.baomidou.mybatisplus.extension.toolkit.Db;
// import com.sugarweb.chatAssistant.domain.po.ChatMessageInfo;
// import dev.langchain4j.data.embedding.Embedding;
// import dev.langchain4j.data.message.AiMessage;
// import dev.langchain4j.data.message.ChatMessage;
// import dev.langchain4j.data.message.SystemMessage;
// import dev.langchain4j.data.message.UserMessage;
// import dev.langchain4j.data.segment.TextSegment;
// import dev.langchain4j.model.chat.ChatLanguageModel;
// import dev.langchain4j.model.chat.StreamingChatLanguageModel;
// import dev.langchain4j.model.embedding.EmbeddingModel;
// import dev.langchain4j.model.input.Prompt;
// import dev.langchain4j.model.input.PromptTemplate;
// import dev.langchain4j.model.output.Response;
// import dev.langchain4j.store.embedding.EmbeddingMatch;
// import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
// import dev.langchain4j.store.embedding.EmbeddingSearchResult;
// import dev.langchain4j.store.embedding.EmbeddingStore;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Component;
//
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// @Component
// @RequiredArgsConstructor
// public class AiChatPipeline {
//
//     private final ChatLanguageModel chatLanguageModel;
//     private final StreamingChatLanguageModel streamingChatLanguageModel;
//     private final EmbeddingModel embeddingModel;
//     private final EmbeddingStore<TextSegment> embeddingStore;
//
//     private final PromptTemplate systemPrompt = getSystemPrompt();
//     private final PromptTemplate userPrompt = getUserPrompt();
//
//     public String chatWithMemory(String question, String memoryId) {
//         Map<String, String> contextVariables = new HashMap<>();
//         contextVariables.put("question", question);
//         //获取相关召回文档
//         String retrievalSegment = getRetrievalSegment(question);
//         contextVariables.put("documents", retrievalSegment);
//
//         //组装发送给大模型的消息
//         List<ChatMessage> messageList = new ArrayList<>();
//         //第一步，配置系统消息
//         Prompt system = systemPrompt.apply(contextVariables);
//         messageList.add(system.toSystemMessage());
//
//         //第二步，配置用户消息历史
//         List<ChatMessageInfo> historyMessage = Db.lambdaQuery(ChatMessageInfo.class)
//                 .eq(ChatMessageInfo::getMemoryId, memoryId)
//                 .in(ChatMessageInfo::getChatRole, "user", "ai")
//                 .orderByAsc(ChatMessageInfo::getCreateTime)
//                 .list();
//         if (historyMessage != null && !historyMessage.isEmpty()) {
//             for (ChatMessageInfo chatMessageInfo : historyMessage) {
//                 ChatMessage chatMessage = getChatMessage(chatMessageInfo);
//                 messageList.add(chatMessage);
//             }
//         }
//
//         //第三步配置当前提问的用户消息
//         Prompt user = userPrompt.apply(contextVariables);
//         messageList.add(user.toUserMessage());
//
//         //获取生成结果
//         Response<AiMessage> generate = chatLanguageModel.generate(messageList);
//         AiMessage aiMessage = generate.content();
//         String aiMessageText = aiMessage.text();
//
//         //保存对话历史
//         ChatMessageInfo userChatMessageInfo = createChatMessageInfo("user", user.text(), memoryId);
//         ChatMessageInfo aiChatMessageInfo = createChatMessageInfo("ai", aiMessageText, memoryId);
//         List<ChatMessageInfo> batchSaveList = new ArrayList<>();
//         batchSaveList.add(userChatMessageInfo);
//         batchSaveList.add(aiChatMessageInfo);
//         Db.saveBatch(batchSaveList);
//
//         // //流式获取结果
//         // StringBuilder streamingResult = new StringBuilder();
//         // streamingChatLanguageModel.generate(messageList, new StreamingResponseHandler<AiMessage>() {
//         //     @Override
//         //     public void onNext(String token) {
//         //         streamingResult.append(token);
//         //     }
//         //
//         //     @Override
//         //     public void onError(Throwable error) {
//         //
//         //     }
//         //
//         // });
//         // System.out.println(streamingResult);
//
//         return aiMessageText;
//     }
//
//     public PromptTemplate getSystemPrompt() {
//         String text = """
//                 你现在是友好的人类【xx】，接下来请根据消息做出回答；
//                 要求：回答结果以口语化的形式表达，返回结果只能是中文。
//
//                 以下是参考文档：
//                 {{documents}}
//                 """;
//         return PromptTemplate.from(text);
//     }
//
//     public PromptTemplate getUserPrompt() {
//         String text = """
//                 {{question}}
//                 """;
//         return PromptTemplate.from(text);
//     }
//
//
//     private ChatMessageInfo createChatMessageInfo(String type, String content, String memoryId) {
//         ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
//         chatMessageInfo.setMemoryId(memoryId);
//         chatMessageInfo.setContent(content);
//         chatMessageInfo.setChatRole(type);
//         chatMessageInfo.setCreateTime(LocalDateTime.now());
//         chatMessageInfo.setUpdateTime(LocalDateTime.now());
//         return chatMessageInfo;
//     }
//
//     private ChatMessage getChatMessage(ChatMessageInfo chatMessageInfo) {
//         if ("user".equals(chatMessageInfo.getChatRole())) {
//             return new UserMessage(chatMessageInfo.getContent());
//         } else if ("ai".equals(chatMessageInfo.getChatRole())) {
//             return new AiMessage(chatMessageInfo.getContent());
//         } else if ("system".equals(chatMessageInfo.getChatRole())) {
//             return new SystemMessage(chatMessageInfo.getContent());
//         }
//         throw new IllegalArgumentException(StrUtil.format("不支持的消息类型,chatId:{}", chatMessageInfo.getChatId()));
//     }
//
//     /**
//      * 获取召回片段
//      */
//     public String getRetrievalSegment(String question) {
//         Response<Embedding> embeddingResponse = embeddingModel.embed(TextSegment.from(question));
//         //获取嵌入向量
//         Embedding embedding = embeddingResponse.content();
//         EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
//                 .queryEmbedding(embedding)
//                 .maxResults(10)
//                 .minScore(0.7)
//                 .build();
//         EmbeddingSearchResult<TextSegment> embeddingSearchResult = embeddingStore.search(embeddingSearchRequest);
//         List<EmbeddingMatch<TextSegment>> embeddingMatchList = embeddingSearchResult.matches();
//         StringBuilder documentStr = new StringBuilder();
//         //装载文档
//         for (EmbeddingMatch<TextSegment> textSegmentEmbeddingMatch : embeddingMatchList) {
//             String text = textSegmentEmbeddingMatch.embedded().text();
//             documentStr.append(text).append("\n");
//         }
//         return documentStr.toString();
//     }
//
// }