package com.sugarweb.digitalHuman.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.service.dto.StageDetailDto;
import com.sugarweb.digitalHuman.service.dto.StagePageQuery;
import com.sugarweb.digitalHuman.service.dto.StageSaveDto;
import com.sugarweb.digitalHuman.service.dto.StageUpdateDto;
import com.sugarweb.digitalHuman.entity.Stage;
import com.sugarweb.framework.orm.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * StageService
 *
 * @author xxd
 * @since 2024/10/15 22:39
 */
@Service
public class StageService {

    @Resource
    private ActorService actorService;
    @Resource
    private ScriptService scriptService;

    public IPage<StageDetailDto> page(StagePageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<Stage>()
                .like(StrUtil.isNotEmpty(query.getStageName()), Stage::getStageName, query.getStageName())
                .orderByDesc(Stage::getCreateTime)
        ).convert(this::buildDetail);
    }

    public Stage getById(String stageId) {
        return Db.getById(stageId, Stage.class);
    }

    public StageDetailDto detail(String stageId) {
        return buildDetail(Db.getById(stageId, Stage.class));
    }

    public StageDetailDto save(StageSaveDto saveDto) {
        Stage stage = new Stage();
        stage.setStageName(saveDto.getStageName());
        stage.setDescription(saveDto.getDescription());
        Db.save(stage);
        return buildDetail(stage);
    }

    public StageDetailDto update(StageUpdateDto updateDto) {
        Stage stage = Db.getById(updateDto.getStageId(), Stage.class);
        if (stage != null) {
            stage.setStageName(updateDto.getStageName());
            stage.setDescription(updateDto.getDescription());
            Db.updateById(stage);
        }
        return buildDetail(stage);
    }

    private StageDetailDto buildDetail(Stage stage) {
        if (stage == null) {
            return null;
        }
        StageDetailDto stageDetailDto = new StageDetailDto();
        stageDetailDto.setStageId(stage.getStageId());
        stageDetailDto.setStageName(stage.getStageName());
        stageDetailDto.setDescription(stage.getDescription());
        return stageDetailDto;
    }

    public void remove(String stageId) {
        Db.removeById(stageId, Stage.class);
    }

}
