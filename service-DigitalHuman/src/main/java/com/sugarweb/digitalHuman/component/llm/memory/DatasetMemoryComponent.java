package com.sugarweb.digitalHuman.component.llm.memory;

import com.sugarweb.digitalHuman.entity.Kb;
import com.sugarweb.digitalHuman.entity.Model;
import com.sugarweb.digitalHuman.component.MilvusEmbeddingStoreFactory;
import com.sugarweb.digitalHuman.component.llm.ModelFactory;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;

import java.util.List;

/**
 * 知识库记忆组件
 *
 * @author xxd
 * @version 1.0
 */
public class DatasetMemoryComponent {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public DatasetMemoryComponent(Kb kb) {
        if (kb == null) {
            throw new IllegalArgumentException("kbInfo is null");
        }
        this.embeddingStore = MilvusEmbeddingStoreFactory.create(kb);
        Model embeddingModel = kb.getEmbeddingModel();
        if (embeddingModel == null) {
            throw new IllegalArgumentException("embeddingModelInfo is null");
        }
        this.embeddingModel = ModelFactory.creatEmbeddingModel(embeddingModel);
    }

    /**
     * 获取召回片段 todo rerank
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
