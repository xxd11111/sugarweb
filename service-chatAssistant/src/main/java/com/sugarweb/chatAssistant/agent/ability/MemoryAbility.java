package com.sugarweb.chatAssistant.agent.ability;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.po.ChatMsg;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;

import java.util.Collections;
import java.util.List;

/**
 * MemoryAbility
 *
 * @author xxd
 * @version 1.0
 */
public class MemoryAbility {

    private EmbeddingModel embeddingModel;

    private EmbeddingStore<TextSegment> embeddingStore;

    public List<ChatMsg> listLastChatMessage(String memoryId, int limit) {
        List<ChatMsg> chatMemoryInfos = Db.lambdaQuery(ChatMsg.class)
                .eq(ChatMsg::getMemoryId, memoryId)
                .orderByDesc(ChatMsg::getCreateTime)
                .last(limit > 0, "limit " + limit)
                .list();
        Collections.reverse(chatMemoryInfos);
        return chatMemoryInfos;
    }

    public void saveBatchChatMessage(List<ChatMsg> chatMemoryInfo) {
        Db.saveBatch(chatMemoryInfo);
    }

    public void saveChatMessage(ChatMsg chatMemoryInfo) {
        Db.save(chatMemoryInfo);
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
