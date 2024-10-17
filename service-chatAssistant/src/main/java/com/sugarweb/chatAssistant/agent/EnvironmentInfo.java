package com.sugarweb.chatAssistant.agent;

import com.sugarweb.chatAssistant.domain.*;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvironmentInfo {

    private AgentInfo agentInfo;

    private StageInfo stageInfo;

    private SceneInfo sceneInfo;

    private MemoryInfo currentMemory;

    private PromptTemplateInfo systemPromptTemplateInfo;

    private PromptTemplateInfo userPromptTemplateInfo;

    private StreamingChatLanguageModel streamingChatLanguageModel;

    private EmbeddingModel embeddingModel;

    private EmbeddingStore<TextSegment> embeddingStore;

    public String getSystemPrompt(Map<String, Object> contextVariables) {
        return systemPromptTemplateInfo.getPrompt(contextVariables);
    }

    public String getUserPrompt(Map<String, Object> contextVariables) {
        return userPromptTemplateInfo.getPrompt(contextVariables);
    }

    public String getCurrentMemoryId() {
        return currentMemory.getMemoryId();
    }

}
