package com.sugarweb.chatAssistant.config;

import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.param.MetricType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ChatAssistantProperties
 *
 * @author xxd
 * @since 2024/10/19 22:51
 */
@Data
@ConfigurationProperties(prefix = "sugarweb.chat-assistant")
public class ChatAssistantProperties {

    private LlmType llmType = LlmType.NONE;

    private OllamaProperties ollama;

    private VectorStoreType vectorStoreType = VectorStoreType.IN_MEMORY;

    private MilvusVectorStoreProperties milvus;


    @Data
    public static class OllamaProperties {

        private String baseUrl = "http://localhost:11434";

        private OllamaChatModelProperties chatModel;

        private OllamaEmbeddingModelProperties embeddingModel;

    }


    @Data
    public static class MilvusVectorStoreProperties {

        private String url;

        private String username;

        private String password;

        private String databaseName;

        private String collectionName;

        private Integer dimension;

        private ConsistencyLevelEnum consistencyLevel;

        private MetricType metricType;

    }

    public enum LlmType {
        NONE,
        OLLAMA;
    }

    public enum VectorStoreType {
        IN_MEMORY,
        MILVUS;
    }

    @Data
    public static class OllamaEmbeddingModelProperties {
        private String modelName;
        /**
         * 默认60秒
         */
        private Integer timeout;
    }

    @Data
    public static class OllamaChatModelProperties {
        private String modelName;
        private Double temperature = 0.75;
        /**
         * 默认60秒
         */
        private Integer timeout = 60000;
    }
}
