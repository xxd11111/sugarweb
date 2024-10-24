package com.sugarweb.chatAssistant.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.application.dto.StageDetailDto;
import com.sugarweb.chatAssistant.application.dto.StagePageQuery;
import com.sugarweb.chatAssistant.application.dto.StageSaveDto;
import com.sugarweb.chatAssistant.application.dto.StageUpdateDto;
import com.sugarweb.chatAssistant.domain.MemoryInfo;
import com.sugarweb.chatAssistant.domain.SceneInfo;
import com.sugarweb.chatAssistant.domain.StageInfo;
import com.sugarweb.framework.orm.PageHelper;
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

    public IPage<StageDetailDto> page(StagePageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<StageInfo>()
                .like(StrUtil.isNotEmpty(query.getStageName()), StageInfo::getStageName, query.getStageName())
                .orderByDesc(StageInfo::getCreateTime)
        ).convert(this::buildDetail);
    }

    public StageDetailDto detail(String stageId) {
        return buildDetail(Db.getById(stageId, StageInfo.class));
    }

    public StageDetailDto save(StageSaveDto saveDto) {
        StageInfo stageInfo = new StageInfo();
        stageInfo.setStageName(saveDto.getStageName());
        stageInfo.setDescription(saveDto.getDescription());
        stageInfo.setCreateTime(LocalDateTime.now());
        stageInfo.setUpdateTime(LocalDateTime.now());
        Db.save(stageInfo);
        return buildDetail(stageInfo);
    }

    public StageDetailDto update(StageUpdateDto updateDto) {
        StageInfo stageInfo = Db.getById(updateDto.getStageId(), StageInfo.class);
        if (stageInfo != null) {
            stageInfo.setStageName(updateDto.getStageName());
            stageInfo.setDescription(updateDto.getDescription());
            stageInfo.setUpdateTime(LocalDateTime.now());
            Db.updateById(stageInfo);
        }
        return buildDetail(stageInfo);
    }

    private StageDetailDto buildDetail(StageInfo stageInfo) {
        if (stageInfo == null) {
            return null;
        }
        StageDetailDto stageDetailDto = new StageDetailDto();
        stageDetailDto.setStageId(stageInfo.getStageId());
        stageDetailDto.setStageName(stageInfo.getStageName());
        stageDetailDto.setDescription(stageInfo.getDescription());
        stageDetailDto.setCreateTime(stageInfo.getCreateTime());
        stageDetailDto.setUpdateTime(stageInfo.getUpdateTime());
        return stageDetailDto;
    }
}
