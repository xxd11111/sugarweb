package com.sugarweb.chatAssistant.application;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.MemoryInfo;
import com.sugarweb.chatAssistant.domain.SceneInfo;
import com.sugarweb.chatAssistant.domain.StageInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * StageService
 *
 * @author xxd
 * @since 2024/10/15 22:39
 */
@Service
public class StageService {

    private final String defaultStageId = "1";
    private final String defaultAgentId = "1";

    public StageInfo defaultStage() {
        StageInfo stageInfo = Db.getById(defaultStageId, StageInfo.class);
        if (stageInfo == null) {
            stageInfo = new StageInfo();
            stageInfo.setStageId(defaultStageId);
            stageInfo.setStageName("哔哩哔哩直播");
            stageInfo.setDescription("哔哩哔哩直播");
            stageInfo.setCreateTime(LocalDateTime.now());
            stageInfo.setUpdateTime(LocalDateTime.now());
            Db.save(stageInfo);
        }
        return stageInfo;

    }

    public SceneInfo defaultScene() {
        //检查今天有没有创建
        List<SceneInfo> list = Db.lambdaQuery(SceneInfo.class)
                .eq(SceneInfo::getStageId, defaultStageId)
                .between(SceneInfo::getCreateTime, LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now())
                .list();
        if (!list.isEmpty()) {
            return list.getFirst();
        } else {
            SceneInfo sceneInfo = new SceneInfo();
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            sceneInfo.setStageId(defaultStageId);
            sceneInfo.setSceneName(date + "直播");
            sceneInfo.setDescription("哔哩哔哩直播");
            sceneInfo.setCreateTime(LocalDateTime.now());
            sceneInfo.setUpdateTime(LocalDateTime.now());
            Db.save(sceneInfo);
            return sceneInfo;
        }
    }

    public MemoryInfo defaultWithSceneMemory(String sceneId) {
        MemoryInfo memoryInfo = Db.lambdaQuery(MemoryInfo.class)
                .eq(MemoryInfo::getAgentId, defaultAgentId)
                .eq(MemoryInfo::getStageId, defaultStageId)
                .eq(MemoryInfo::getSceneId, sceneId)
                .one();
        if (memoryInfo == null) {
            memoryInfo = new MemoryInfo();
            memoryInfo.setAgentId(defaultAgentId);
            memoryInfo.setStageId(defaultStageId);
            memoryInfo.setSceneId(sceneId);
            memoryInfo.setCreateTime(LocalDateTime.now());
            memoryInfo.setUpdateTime(LocalDateTime.now());
            Db.save(memoryInfo);
        }
        return memoryInfo;
    }
}
