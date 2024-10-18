package com.sugarweb.chatAssistant.agent.ability.memory;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.constans.ChatRole;
import com.sugarweb.chatAssistant.domain.BlblUserAction;
import com.sugarweb.chatAssistant.domain.ChatMsg;
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

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public MemoryAbility(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) {
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    public List<ChatMsg> listLastChatMessage(String memoryId, int limit) {
        List<ChatMsg> chatMemoryInfos = Db.lambdaQuery(ChatMsg.class)
                .eq(ChatMsg::getMemoryId, memoryId)
                .orderByDesc(ChatMsg::getCreateTime)
                .in(ChatMsg::getChatRole, ChatRole.USER.getCode(), ChatRole.ASSISTANT.getCode())
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

    public void saveBlblUserAction(BlblUserAction object) {
        Db.save(object);
    }

    public List<BlblUserAction> listUserAction(String blblUid) {
        return Db.lambdaQuery(BlblUserAction.class)
                .eq(BlblUserAction::getBlblUid, blblUid)
                .list();
    }

}
