package com.sugarweb.chatAssistant.demo;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.index.CreateIndexParam;

import java.util.List;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
public class LangChain4jDemo {
    public static void main(String[] args) {

        MilvusEmbeddingStore embeddingStore = MilvusEmbeddingStore.builder()
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

        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://192.168.193.151:11434")
                .modelName("nomic-embed-text")
                .build();

        TextSegment segment1 = TextSegment.from("I like football.");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);

        TextSegment segment2 = TextSegment.from("The weather is good today.");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);

        Embedding queryEmbedding = embeddingModel.embed("What is your favourite sport?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        System.out.println(embeddingMatch.score()); // 0.8144287765026093
        System.out.println(embeddingMatch.embedded().text()); // I like football.

        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:3b")
                .format("json")
                .build();
        String json = model.generate("Give me a JSON with 2 fields: name and age of a John Doe, 42");
        System.out.println();
    }
}
