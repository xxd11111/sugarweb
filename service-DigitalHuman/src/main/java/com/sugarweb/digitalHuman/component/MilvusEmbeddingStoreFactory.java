package com.sugarweb.digitalHuman.component;

import com.sugarweb.digitalHuman.config.ApplicationProperties;
import com.sugarweb.digitalHuman.entity.Kb;
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
        ApplicationProperties bean = BeanUtil.getBean(ApplicationProperties.class);
        ApplicationProperties.MilvusVectorStoreProperties vectorStoreProperties = bean.getMilvus();
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

    public static MilvusEmbeddingStore create(Kb kb) {
        ApplicationProperties bean = BeanUtil.getBean(ApplicationProperties.class);
        ApplicationProperties.MilvusVectorStoreProperties vectorStoreProperties = bean.getMilvus();
        return MilvusEmbeddingStore.builder()
                .uri(vectorStoreProperties.getUrl())
                .username(vectorStoreProperties.getUsername())
                .password(vectorStoreProperties.getPassword())
                .databaseName(vectorStoreProperties.getDatabaseName())
                .collectionName(kb.getCollectionName())
                .dimension(kb.getDimension())
                .consistencyLevel(vectorStoreProperties.getConsistencyLevel())
                .indexType(IndexType.HNSW)
                .metricType(vectorStoreProperties.getMetricType())
                .build();
    }

}
