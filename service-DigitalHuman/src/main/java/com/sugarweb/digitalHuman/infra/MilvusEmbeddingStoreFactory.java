package com.sugarweb.digitalHuman.infra;

import com.sugarweb.digitalHuman.config.ChatAssistantProperties;
import com.sugarweb.digitalHuman.domain.KbInfo;
import com.sugarweb.framework.utils.BeanUtil;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import io.milvus.param.IndexType;

/**
 * MilvusEmbeddingStoreFactory
 *
 * @author xxd
 * @version 1.0
 */
public class MilvusEmbeddingStoreFactory {

    public static MilvusEmbeddingStore create(String collectionName, Integer dimension) {
        ChatAssistantProperties bean = BeanUtil.getBean(ChatAssistantProperties.class);
        ChatAssistantProperties.MilvusVectorStoreProperties vectorStoreProperties = bean.getMilvus();
        return MilvusEmbeddingStore.builder()
                .uri(vectorStoreProperties.getUrl())
                .username(vectorStoreProperties.getUsername())
                .password(vectorStoreProperties.getPassword())
                .databaseName(vectorStoreProperties.getDatabaseName())
                .collectionName(collectionName)
                .dimension(dimension)
                .consistencyLevel(vectorStoreProperties.getConsistencyLevel())
                .indexType(IndexType.HNSW)
                .metricType(vectorStoreProperties.getMetricType())
                .build();
    }

    public static MilvusEmbeddingStore create(KbInfo kbInfo) {
        ChatAssistantProperties bean = BeanUtil.getBean(ChatAssistantProperties.class);
        ChatAssistantProperties.MilvusVectorStoreProperties vectorStoreProperties = bean.getMilvus();
        return MilvusEmbeddingStore.builder()
                .uri(vectorStoreProperties.getUrl())
                .username(vectorStoreProperties.getUsername())
                .password(vectorStoreProperties.getPassword())
                .databaseName(vectorStoreProperties.getDatabaseName())
                .collectionName(kbInfo.getCollectionName())
                .dimension(kbInfo.getDimension())
                .consistencyLevel(vectorStoreProperties.getConsistencyLevel())
                .indexType(IndexType.HNSW)
                .metricType(vectorStoreProperties.getMetricType())
                .build();
    }

}
