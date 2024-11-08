package com.sugarweb.digitalHuman.infra.agent.memory;

import com.sugarweb.digitalHuman.domain.KbInfo;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.digitalHuman.infra.MilvusEmbeddingStoreFactory;
import com.sugarweb.digitalHuman.infra.llm.ModelFactory;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;

import java.util.List;

/**
 * 知识库记忆组件
 *
 * @author xxd
 * @version 1.0
 */
public class KbMemoryComponent {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public KbMemoryComponent(KbInfo kbInfo) {
        if (kbInfo == null) {
            throw new IllegalArgumentException("kbInfo is null");
        }
        this.embeddingStore = MilvusEmbeddingStoreFactory.create(kbInfo);
        ModelInfo embeddingModelInfo = kbInfo.getEmbeddingModelInfo();
        if (embeddingModelInfo == null) {
            throw new IllegalArgumentException("embeddingModelInfo is null");
        }
        this.embeddingModel = ModelFactory.creatEmbeddingModel(embeddingModelInfo);
    }

    /**
     * 获取召回片段
     */
    public String getRetrievalSegment(String queryMessage) {
        TextSegment textSegment = TextSegment.from(queryMessage);
        Response<Embedding> embeddingResponse = embeddingModel.embed(textSegment);
        //获取嵌入向量
        Embedding embedding = embeddingResponse.content();
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding)
                .maxResults(10)
                .minScore(0.7)
                .build();
        EmbeddingSearchResult<TextSegment> embeddingSearchResult = embeddingStore.search(embeddingSearchRequest);
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
