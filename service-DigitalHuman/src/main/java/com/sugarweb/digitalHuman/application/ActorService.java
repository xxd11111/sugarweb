package com.sugarweb.digitalHuman.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.ActorDetailDto;
import com.sugarweb.digitalHuman.application.dto.ActorPageQuery;
import com.sugarweb.digitalHuman.application.dto.ActorSaveDto;
import com.sugarweb.digitalHuman.application.dto.ActorUpdateDto;
import com.sugarweb.digitalHuman.domain.Actor;
import com.sugarweb.digitalHuman.domain.Dataset;
import com.sugarweb.digitalHuman.domain.Model;
import com.sugarweb.framework.orm.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * ActorService
 *
 * @author xxd
 * @since 2024/10/15 22:40
 */
@Service
public class ActorService {

    @Resource
    private DatasetService datasetService;

    public Actor getById(String actorId) {
        Actor actor = Db.getById(actorId, Actor.class);
        if (StrUtil.isNotEmpty(actor.getChatModelId())) {
            Model model = Db.getById(actorId, Model.class);
            // actor.setChatModelInfo(model);
        }
        if (StrUtil.isNotEmpty(actor.getDatasetId())) {
            Dataset dataset = datasetService.getById(actor.getDatasetId());
            // actor.setKbInfo(dataset);
        }
        return actor;
    }

    public Actor save(Actor actor) {
        Db.save(actor);
        return actor;
    }

    public Actor update(Actor actor) {
        Db.updateById(actor);
        return actor;
    }

    public IPage<ActorDetailDto> page(ActorPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<Actor>()
                .like(StrUtil.isNotEmpty(query.getActorName()), Actor::getActorName, query.getActorName())
                .orderByDesc(Actor::getCreateTime)
        ).convert(this::buildActorDetailDto);
    }

    public ActorDetailDto detail(String actorId) {
        return buildActorDetailDto(getById(actorId));
    }

    public ActorDetailDto save(ActorSaveDto saveDto) {
        Actor actor = new Actor();
        actor.setActorName(saveDto.getActorName());
        save(actor);
        return buildActorDetailDto(actor);
    }

    public ActorDetailDto update(ActorUpdateDto updateDto) {
        Actor actor = Db.getById(updateDto.getActorId(), Actor.class);
        if (actor != null) {
            actor.setActorName(updateDto.getActorName());
            Db.updateById(actor);
        }
        return buildActorDetailDto(actor);
    }

    private ActorDetailDto buildActorDetailDto(Actor actor) {
        if (actor == null) {
            return null;
        }
        ActorDetailDto actorDetailDto = new ActorDetailDto();
        actorDetailDto.setActorId(actor.getActorId());
        actorDetailDto.setActorName(actor.getActorName());
        return actorDetailDto;
    }

    public void remove(String actorId) {
        Db.removeById(actorId, Actor.class);
    }
}
