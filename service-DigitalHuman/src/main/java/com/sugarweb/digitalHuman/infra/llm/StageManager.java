package com.sugarweb.digitalHuman.infra.llm;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.domain.Actor;
import com.sugarweb.digitalHuman.domain.Script;
import com.sugarweb.digitalHuman.domain.Stage;
import com.sugarweb.digitalHuman.domain.StagePerformance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StageManager
 *
 * @author xxd
 * @version 1.0
 */
@Component
@Slf4j
public class StageManager implements DisposableBean {

    private final Map<String, AutoStage> runningStageMap = new ConcurrentHashMap<>();

    public void startStage(Stage stage) {
        String stageId = stage.getStageId();
        try {
            AutoStage autoStage = runningStageMap.computeIfAbsent(stageId, a -> load(stage));
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
    private AutoStage load(Stage stage) {
        String scriptId = stage.getScriptId();
        Script script = Db.getById(scriptId, Script.class);

        String actorId = stage.getActorId();
        Actor actor = Db.getById(actorId, Actor.class);

        StagePerformance stagePerformance = new StagePerformance();
        stagePerformance.setTitle(stage.getStageName());
        stagePerformance.setStageId(stage.getStageId());
        stagePerformance.setStageName(stage.getStageName());
        stagePerformance.setScriptId(stage.getScriptId());
        stagePerformance.setScriptName(script.getScriptName());
        stagePerformance.setActorId(actor.getActorId());
        stagePerformance.setActorName(actor.getActorName());
        stagePerformance.setStartTime(LocalDateTime.now());
        // stagePerformance.setEndTime();
        Db.save(stagePerformance);

        StageContext stageContext = StageContext.builder()
                .stage(stage)
                .actor(actor)
                .script(script)
                .stagePerformance(stagePerformance)
                .dataset(null)
                .build();
        if (stageContext == null) {
            String errorMessage = "Failed to create default environment info for stage: " + stage.getStageId();
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
        AutoStage autoStage = new AutoStage(stageContext);
        log.info("New actor created and started: {}, at time: {}", stage.getStageId(), System.currentTimeMillis());
        return autoStage;
    }

    @Override
    public void destroy() {
        runningStageMap.values().forEach(AutoStage::stop);
    }
}
