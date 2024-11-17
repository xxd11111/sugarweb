package com.sugarweb.digitalHuman.config;

import com.sugarweb.digitalHuman.constants.Common;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import io.milvus.param.IndexType;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * RagBeanConfiguration
 *
 * @author xxd
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties({ApplicationProperties.class, BlblClientProperties.class})
public class ApplicationConfiguration {

    @Resource
    private ApplicationProperties applicationProperties;

    @Resource
    private BlblClientProperties blblClientProperties;

    // @Bean
    // @ConditionalOnProperty(name = Common.CONFIG_PREFIX + ".llm-type", havingValue = "ollama")
    public EmbeddingModel embeddingModel() {
        ApplicationProperties.OllamaProperties ollamaProperties = applicationProperties.getOllama();
        ApplicationProperties.OllamaEmbeddingModelProperties embeddingModel = ollamaProperties.getEmbeddingModel();

        return OllamaEmbeddingModel.builder()
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(embeddingModel.getModelName())
                .timeout(Duration.ofMillis(embeddingModel.getTimeout()))
                .build();
    }

    // @Bean
    // @ConditionalOnProperty(name = Common.CONFIG_PREFIX + ".llm-type", havingValue = "ollama")
    public ChatLanguageModel chatLanguageModel() {
        ApplicationProperties.OllamaProperties ollamaProperties = applicationProperties.getOllama();
        ApplicationProperties.OllamaChatModelProperties chatModel = ollamaProperties.getChatModel();
        return OllamaChatModel.builder()
                .temperature(chatModel.getTemperature())
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(chatModel.getModelName())
                .timeout(Duration.ofMillis(chatModel.getTimeout()))
                .build();
    }

    // @Bean
    // @ConditionalOnProperty(name = Common.CONFIG_PREFIX + ".llm-type", havingValue = "ollama")
    public StreamingChatLanguageModel streamingChatLanguageModel() {
        ApplicationProperties.OllamaProperties ollamaProperties = applicationProperties.getOllama();
        ApplicationProperties.OllamaChatModelProperties chatModel = ollamaProperties.getChatModel();
        return OllamaStreamingChatModel.builder()
                .temperature(chatModel.getTemperature())
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(chatModel.getModelName())
                .timeout(Duration.ofMillis(chatModel.getTimeout()))
                .build();
    }

    // @Bean
    // @ConditionalOnProperty(name = Common.CONFIG_PREFIX + ".vector-store-type", havingValue = "milvus")
    public EmbeddingStore<TextSegment> milvusEmbeddingStore() {
        ApplicationProperties.MilvusVectorStoreProperties vectorStore = applicationProperties.getMilvus();

        return MilvusEmbeddingStore.builder()
                .uri(vectorStore.getUrl())
                .username(vectorStore.getUsername())
                .password(vectorStore.getPassword())
                .databaseName(vectorStore.getDatabaseName())
                .collectionName(vectorStore.getCollectionName())
                .dimension(vectorStore.getDimension())
                .consistencyLevel(vectorStore.getConsistencyLevel())
                .indexType(IndexType.HNSW)
                .metricType(vectorStore.getMetricType())
                .build();
    }

}
