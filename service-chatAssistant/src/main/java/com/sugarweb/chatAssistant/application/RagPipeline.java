package com.sugarweb.chatAssistant.application;

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
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.param.MetricType;

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

    private static final InMemoryEmbeddingStore<TextSegment> inMemoryEmbeddingStore = new InMemoryEmbeddingStore<>();
    private static boolean userInMemoryVectorStore = true;
    private static final ChatLanguageModel chatLanguageModel = OllamaChatModel.builder()
            .logRequests(true)
            .logResponses(true)
            .listeners(List.of(new ChatModelListener() {
                @Override
                public void onResponse(ChatModelResponseContext responseContext) {
                }
            }))
            .baseUrl("http://localhost:11434")
            .modelName("qwen2.5:7b")
            .build();

    private static final EmbeddingModel embeddingModel =OllamaEmbeddingModel.builder()
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

    public String chat(String eventMessage) {
        //获取参考文档

        //获取嵌入模型
        EmbeddingModel embeddingModel = getEmbeddingModel();
        TextSegment textSegment = TextSegment.from(eventMessage);
        Response<Embedding> embeddingResponse = embeddingModel.embed(textSegment);
        //获取嵌入向量
        Embedding embedding = embeddingResponse.content();
        //获取向量库
        EmbeddingStore<TextSegment> vectorStore = getEmbeddingStore();
        //根据内容获取相关文档
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding)
                .maxResults(10)
                .minScore(0.7)
                .build();
        EmbeddingSearchResult<TextSegment> embeddingSearchResult = vectorStore.search(embeddingSearchRequest);
        List<EmbeddingMatch<TextSegment>> embeddingMatchList = embeddingSearchResult.matches();
        StringBuilder documentStr = new StringBuilder();
        //装载文档
        for (EmbeddingMatch<TextSegment> textSegmentEmbeddingMatch : embeddingMatchList) {
            String text = textSegmentEmbeddingMatch.embedded().text();
            documentStr.append(text).append("\n");
        }

        ChatLanguageModel chatModel = getChatModel();
        //组装发送给大模型的消息
        List<ChatMessage> messages = new ArrayList<>();
        //配置系统消息
        String text = """
                你现在是个直播机器人【炫东】，请根据事件消息做出互动,要求活泼，搞怪。
                                
                以下是参考文档：
                {{documents}}
                """;
        PromptTemplate promptTemplate = new PromptTemplate(text);

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("documents", documentStr);
        Prompt prompt = promptTemplate.apply(variables);

        SystemMessage systemMessage = new SystemMessage(prompt.text());
        messages.add(systemMessage);
        //配置用户消息
        UserMessage userMessage = new UserMessage(eventMessage);
        messages.add(userMessage);

        //获取生成结果
        Response<AiMessage> generate = chatModel.generate(messages);
        AiMessage content = generate.content();

        //todo 将Message结果入库
        return content.text();
    }

    /**
     * 获取聊天大模型
     */
    public ChatLanguageModel getChatModel(){
        return chatLanguageModel;
    }

    /**
     * 获取Embedding模型
     */
    public EmbeddingModel getEmbeddingModel(){
        return embeddingModel;
    }

    /**
     * 获取向量存储库
     */
    public EmbeddingStore<TextSegment> getEmbeddingStore(){
        return inMemoryEmbeddingStore;


        // if (userInMemoryVectorStore){
        //     return inMemoryEmbeddingStore;
        // }else {
        //     return embeddingStore;
        // }
    }

    /**
     * 获取召回片段
     */
    public void getRetrievalSegment(){

    }

    /**
     * 获取提示词
     */
    public void getPrompt(){

    }

    /**
     * 获取系统提示词
     */
    public void getSystemPrompt(){

    }


}
