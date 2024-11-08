package com.sugarweb.digitalHuman.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.StageDetailDto;
import com.sugarweb.digitalHuman.application.dto.StagePageQuery;
import com.sugarweb.digitalHuman.application.dto.StageSaveDto;
import com.sugarweb.digitalHuman.application.dto.StageUpdateDto;
import com.sugarweb.digitalHuman.domain.AgentInfo;
import com.sugarweb.digitalHuman.domain.SceneInfo;
import com.sugarweb.digitalHuman.domain.StageInfo;
import com.sugarweb.framework.orm.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * StageService
 *
 * @author xxd
 * @since 2024/10/15 22:39
 */
@Service
public class StageService {

    @Resource
    private AgentService agentService;
    @Resource
    private SceneService sceneService;

    public IPage<StageDetailDto> page(StagePageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<StageInfo>()
                .like(StrUtil.isNotEmpty(query.getStageName()), StageInfo::getStageName, query.getStageName())
                .orderByDesc(StageInfo::getCreateTime)
        ).convert(this::buildDetail);
    }

    public StageInfo getById(String stageId) {
        StageInfo stageInfo = Db.getById(stageId, StageInfo.class);
        if (stageInfo == null) {
            return null;
        }
        String agentId = stageInfo.getAgentId();
        if (StrUtil.isNotEmpty(agentId)){
            AgentInfo agentInfo = agentService.getById(agentId);
            stageInfo.setAgentInfo(agentInfo);
        }
        if (StrUtil.isNotEmpty(stageInfo.getSceneId())){
            SceneInfo sceneInfo = sceneService.getById(stageInfo.getSceneId());
            stageInfo.setSceneInfo(sceneInfo);
        }
        return stageInfo;
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

    public void remove(String stageId) {
        Db.removeById(stageId, StageInfo.class);
    }

}
