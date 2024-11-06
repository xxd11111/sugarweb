package com.sugarweb.digitalHuman.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.SceneDetailDto;
import com.sugarweb.digitalHuman.application.dto.ScenePageQuery;
import com.sugarweb.digitalHuman.application.dto.SceneSaveDto;
import com.sugarweb.digitalHuman.application.dto.SceneUpdateDto;
import com.sugarweb.digitalHuman.domain.SceneInfo;
import com.sugarweb.framework.orm.PageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * SceneService
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class SceneService {

    public IPage<SceneDetailDto> page(ScenePageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<SceneInfo>()
                .like(StrUtil.isNotEmpty(query.getSceneName()), SceneInfo::getSceneName, query.getSceneName())
                .orderByDesc(SceneInfo::getCreateTime)
        ).convert(this::buildSceneDetailDto);
    }

    public SceneDetailDto detail(String sceneId) {
        SceneInfo sceneInfo = Db.getById(sceneId, SceneInfo.class);
        return buildSceneDetailDto(sceneInfo);
    }

    public SceneDetailDto save(SceneSaveDto saveDto) {
        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setSceneName(saveDto.getSceneName());
        sceneInfo.setDescription(saveDto.getDescription());
        sceneInfo.setCreateTime(LocalDateTime.now());
        sceneInfo.setUpdateTime(LocalDateTime.now());
        Db.save(sceneInfo);
        return buildSceneDetailDto(sceneInfo);
    }

    public SceneDetailDto update(SceneUpdateDto updateDto) {
        SceneInfo sceneInfo = Db.getById(updateDto.getSceneId(), SceneInfo.class);
        if (sceneInfo != null) {
            sceneInfo.setSceneName(updateDto.getSceneName());
            sceneInfo.setDescription(updateDto.getDescription());
            sceneInfo.setUpdateTime(LocalDateTime.now());
            Db.updateById(sceneInfo);
        }
        return buildSceneDetailDto(sceneInfo);
    }

    private SceneDetailDto buildSceneDetailDto(SceneInfo sceneInfo) {
        if (sceneInfo == null) {
            return null;
        }
        SceneDetailDto sceneDetailDto = new SceneDetailDto();
        sceneDetailDto.setSceneId(sceneInfo.getSceneId());
        sceneDetailDto.setSceneName(sceneInfo.getSceneName());
        sceneDetailDto.setDescription(sceneInfo.getDescription());
        sceneDetailDto.setCreateTime(sceneInfo.getCreateTime());
        sceneDetailDto.setUpdateTime(sceneInfo.getUpdateTime());
        return sceneDetailDto;
    }

    public void remove(String sceneId) {
        Db.removeById(sceneId, SceneInfo.class);
    }
}
