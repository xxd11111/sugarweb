package com.sugarweb.digitalHuman.agent;

import com.sugarweb.digitalHuman.domain.*;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ExecutorService;

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

    private ExecutorService executor;

    private AgentInfo agentInfo;

    private StageInfo stageInfo;

    private SceneInfo sceneInfo;

    private MemoryInfo currentMemory;

    private PromptTemplateInfo systemPromptTemplateInfo;

    private KbInfo kbInfo;

    public String getSystemPrompt(Map<String, Object> contextVariables) {
        return systemPromptTemplateInfo.getPrompt(contextVariables);
    }

    public String getCurrentMemoryId() {
        return currentMemory.getMemoryId();
    }

}
