package com.sugarweb.chatAssistant.agent;

import com.sugarweb.chatAssistant.application.AgentService;
import com.sugarweb.chatAssistant.application.PromptService;
import com.sugarweb.chatAssistant.application.StageService;
import com.sugarweb.chatAssistant.domain.SceneInfo;
import com.sugarweb.framework.utils.BeanUtil;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * AgentManager
 *
 * @author xxd
 * @version 1.0
 */
@Component
@Slf4j
public class AgentManager implements DisposableBean {

    @Resource
    private AgentService agentService;
    @Resource
    private StageService stageService;
    @Resource
    private PromptService promptService;

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private final Map<String, AutoAgent> autoAgentMap = new ConcurrentHashMap<>();

    public void startAgent(String agentId) {
        try {
            AutoAgent autoAgent = getOrCreateAgent(agentId);
            if (!autoAgent.isRunning()) {
                autoAgent.start();
                log.info("Agent started: {}", agentId);
            } else {
                log.info("Agent already running: {}", agentId);
            }
        } catch (Exception e) {
            // 记录异常信息
            log.error("Error starting agent: {}", agentId, e);
        }
    }

    public void stopAgent(String agentId) {
        try {
            AutoAgent autoAgent = autoAgentMap.get(agentId);
            if (autoAgent != null) {
                autoAgent.stop();
                log.info("Agent stopped: {}", agentId);
            } else {
                log.warn("Agent not found: {}", agentId);
            }
        } catch (Exception e) {
            // 记录异常信息
            log.error("Error stopping agent: {}", agentId, e);
        }
    }

    /**
     * 获取或创建一个AutoAgent实例
     */
    private AutoAgent getOrCreateAgent(String agentId) {
        return autoAgentMap.computeIfAbsent(agentId, this::createAndStartNewAgent);
    }

    private AutoAgent createAndStartNewAgent(String agentId) {
        EnvironmentInfo agentEnvironmentInfo = defaultEnvironmentInfo();
        if (agentEnvironmentInfo == null) {
            String errorMessage = "Failed to create default environment info for agentId: " + agentId;
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
        AutoAgent autoAgent = new AutoAgent(executor, agentEnvironmentInfo);
        log.info("New agent created and started: {}, at time: {}", agentId, System.currentTimeMillis());
        return autoAgent;
    }

    private EnvironmentInfo defaultEnvironmentInfo() {
        SceneInfo sceneInfo = stageService.defaultScene();
        if (sceneInfo == null) {
            throw new IllegalStateException("Default scene info is null");
        }
        return EnvironmentInfo.builder()
                .agentInfo(agentService.defaultAgentInfo())
                .stageInfo(stageService.defaultStage())
                .sceneInfo(sceneInfo)
                .currentMemory(stageService.defaultWithSceneMemory(sceneInfo.getSceneId()))
                .systemPromptTemplateInfo(promptService.defaultSystemPrompt())
                .userPromptTemplateInfo(promptService.defaultUserPrompt())
                .streamingChatLanguageModel(BeanUtil.getBean(StreamingChatLanguageModel.class))
                .embeddingModel(BeanUtil.getBean(EmbeddingModel.class))
                .embeddingStore(BeanUtil.getBean(EmbeddingStore.class))
                .build();
    }

    @Override
    public void destroy() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                log.warn("Executor service did not terminate within the specified time. Attempting to force shutdown.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for executor service to terminate: {}", e.getMessage(), e);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Unexpected exception while shutting down executor service: {}", e.getMessage(), e);
        } finally {
            log.info("Executor service shut down");
        }
    }
}
