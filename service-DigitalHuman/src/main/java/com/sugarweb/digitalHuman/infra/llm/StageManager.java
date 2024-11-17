package com.sugarweb.digitalHuman.infra.llm;

import com.sugarweb.digitalHuman.domain.Actor;
import com.sugarweb.digitalHuman.domain.StagePerformance;
import com.sugarweb.digitalHuman.domain.Script;
import com.sugarweb.digitalHuman.domain.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * StageManager
 *
 * @author xxd
 * @version 1.0
 */
@Component
@Slf4j
public class StageManager implements DisposableBean {

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private final Map<String, AutoStage> runningStageMap = new ConcurrentHashMap<>();

    public void startStage(Stage stage) {
        String stageId = stage.getStageId();
        try {
            AutoStage autoStage = runningStageMap.computeIfAbsent(stageId, a -> loadAutoActor(stage));
            if (autoStage.isRunning()) {
                log.info("Stage already running: {}", stageId);
            } else {
                autoStage.start();
                log.info("Stage started: {}", stageId);
            }
        } catch (Exception e) {
            // 记录异常信息
            log.error("Error starting Actor: {}", stageId, e);
        }
    }

    public void stopStage(String stageId) {
        try {
            AutoStage autoStage = runningStageMap.get(stageId);
            if (autoStage != null) {
                autoStage.stop();
                log.info("Stage stopped: {}", stageId);
            } else {
                log.warn("Stage not found: {}", stageId);
            }
            runningStageMap.remove(stageId);
        } catch (Exception e) {
            // 记录异常信息
            log.error("Error stopping Stage: {}", stageId, e);
        }
    }

    /**
     * 加载AutoActor实例
     */
    private AutoStage loadAutoActor(Stage stage) {
        Actor actor = stage.getActor();
        Script script = stage.getScript();
        StagePerformance stagePerformance = new StagePerformance();
        StageContext stageContext = StageContext.builder()
                .executor(executor)
                .stage(stage)
                .actor(actor)
                .script(script)
                .stagePerformance(stagePerformance)
                .build();
        if (stageContext == null) {
            String errorMessage = "Failed to create default environment info for stage: " + stage.getStageId();
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
        AutoStage autoStage = new AutoStage(executor, stageContext);
        log.info("New actor created and started: {}, at time: {}", stage.getStageId(), System.currentTimeMillis());
        return autoStage;
    }

    @Override
    public void destroy() {
        runningStageMap.values().forEach(AutoStage::stop);
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
