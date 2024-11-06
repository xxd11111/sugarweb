package com.sugarweb.digitalHuman.infra.agent;

import com.sugarweb.digitalHuman.application.SceneService;
import com.sugarweb.digitalHuman.domain.AgentInfo;
import com.sugarweb.digitalHuman.domain.PerformanceInfo;
import com.sugarweb.digitalHuman.domain.SceneInfo;
import com.sugarweb.digitalHuman.domain.StageInfo;
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
public class PerformanceManager implements DisposableBean {

    @Resource
    private SceneService sceneService;

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private final Map<String, AutoAgent> stageMap = new ConcurrentHashMap<>();

    public void startStage(StageInfo stageInfo) {
        String stageId = stageInfo.getStageId();
        try {
            AutoAgent autoAgent = stageMap.computeIfAbsent(stageId, a -> loadAutoAgent(stageInfo));
            if (!autoAgent.isRunning()) {
                autoAgent.start();
                log.info("Stage started: {}", stageId);
            } else {
                log.info("Stage already running: {}", stageId);
            }
        } catch (Exception e) {
            // 记录异常信息
            log.error("Error starting Agent: {}", stageId, e);
        }
    }

    public void stopAgent(String stageId) {
        try {
            AutoAgent autoAgent = stageMap.get(stageId);
            if (autoAgent != null) {
                autoAgent.stop();
                log.info("Agent stopped: {}", stageId);
            } else {
                log.warn("Agent not found: {}", stageId);
            }
            stageMap.remove(stageId);
        } catch (Exception e) {
            // 记录异常信息
            log.error("Error stopping agent: {}", stageId, e);
        }
    }

    /**
     * 加载AutoAgent实例
     */
    private AutoAgent loadAutoAgent(StageInfo stageInfo) {
        AgentInfo agentInfo = stageInfo.getAgentInfo();
        SceneInfo sceneInfo = stageInfo.getSceneInfo();
        PerformanceInfo performanceInfo = new PerformanceInfo();
        EnvironmentContext environmentContext = EnvironmentContext.builder()
                .executor(executor)
                .stageInfo(stageInfo)
                .agentInfo(agentInfo)
                .sceneInfo(sceneInfo)
                .performanceInfo(performanceInfo)
                .build();
        if (environmentContext == null) {
            String errorMessage = "Failed to create default environment info for stage: " + stageInfo.getStageId();
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
        AutoAgent autoAgent = new AutoAgent(executor, environmentContext);
        log.info("New agent created and started: {}, at time: {}", stageInfo.getStageId(), System.currentTimeMillis());
        return autoAgent;
    }

    @Override
    public void destroy() {
        stageMap.values().forEach(AutoAgent::stop);
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
