package com.sugarweb.chatAssistant;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.param.MetricType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * RagBeanConfiguration
 *
 * @author xxd
 * @version 1.0
 */
@Configuration
public class RagBeanConfiguration {

    @Bean
    public EmbeddingModel embeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("nomic-embed-text")
                .build();
    }

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OllamaChatModel.builder()
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
    }

    @Bean
    public StreamingChatLanguageModel streamingChatLanguageModel() {
        return OllamaStreamingChatModel.builder()
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
    }

    @Bean
    public EmbeddingStore<TextSegment> milvusEmbeddingStore() {
        return MilvusEmbeddingStore.builder()
                .uri("http://192.168.193.151:19530")
                .username("root")
                .password("milvus")
                .databaseName("default")
                .collectionName("vector_store4")
                .dimension(768)
                .consistencyLevel(ConsistencyLevelEnum.STRONG)
                // .indexType(IndexType.IVF_FLAT)
                .metricType(MetricType.COSINE)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(EmbeddingStore.class)
    public EmbeddingStore<TextSegment> inMemoryEmbeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

}
