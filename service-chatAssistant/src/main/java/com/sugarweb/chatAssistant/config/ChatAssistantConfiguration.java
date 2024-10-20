package com.sugarweb.chatAssistant.config;

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
@EnableConfigurationProperties({ChatAssistantProperties.class, BlblClientProperties.class})
public class ChatAssistantConfiguration {

    @Resource
    private ChatAssistantProperties chatAssistantProperties;

    @Resource
    private BlblClientProperties blblClientProperties;

    @Bean
    @ConditionalOnProperty(name = "sugarweb.chat-assistant.llm-type", havingValue = "ollama")
    public EmbeddingModel embeddingModel() {
        ChatAssistantProperties.OllamaProperties ollamaProperties = chatAssistantProperties.getOllama();
        ChatAssistantProperties.OllamaEmbeddingModelProperties embeddingModel = ollamaProperties.getEmbeddingModel();

        return OllamaEmbeddingModel.builder()
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(embeddingModel.getModelName())
                .timeout(Duration.ofMillis(embeddingModel.getTimeout()))
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "sugarweb.chat-assistant.llm-type", havingValue = "ollama")
    public ChatLanguageModel chatLanguageModel() {
        ChatAssistantProperties.OllamaProperties ollamaProperties = chatAssistantProperties.getOllama();
        ChatAssistantProperties.OllamaChatModelProperties chatModel = ollamaProperties.getChatModel();
        return OllamaChatModel.builder()
                .temperature(chatModel.getTemperature())
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(chatModel.getModelName())
                .timeout(Duration.ofMillis(chatModel.getTimeout()))
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "sugarweb.chat-assistant.llm-type", havingValue = "ollama")
    public StreamingChatLanguageModel streamingChatLanguageModel() {
        ChatAssistantProperties.OllamaProperties ollamaProperties = chatAssistantProperties.getOllama();
        ChatAssistantProperties.OllamaChatModelProperties chatModel = ollamaProperties.getChatModel();
        return OllamaStreamingChatModel.builder()
                .temperature(chatModel.getTemperature())
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(chatModel.getModelName())
                .timeout(Duration.ofMillis(chatModel.getTimeout()))
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "sugarweb.chat-assistant.vector-store-type", havingValue = "milvus")
    public EmbeddingStore<TextSegment> milvusEmbeddingStore() {
        ChatAssistantProperties.MilvusVectorStoreProperties vectorStore = chatAssistantProperties.getMilvus();


        return MilvusEmbeddingStore.builder()
                .uri(vectorStore.getUrl())
                .username(vectorStore.getUsername())
                .password(vectorStore.getPassword())
                .databaseName(vectorStore.getDatabaseName())
                .collectionName(vectorStore.getCollectionName())
                .dimension(vectorStore.getDimension())
                .consistencyLevel(vectorStore.getConsistencyLevel())
                // .indexType(IndexType.IVF_FLAT)
                .metricType(vectorStore.getMetricType())
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "sugarweb.chat-assistant.vector-store-type", havingValue = "in-memory")
    public EmbeddingStore<TextSegment> inMemoryEmbeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

}
