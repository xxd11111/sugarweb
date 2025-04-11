package com.sugarweb.digitalHuman.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.service.dto.ActorDetailDto;
import com.sugarweb.digitalHuman.service.dto.ActorPageQuery;
import com.sugarweb.digitalHuman.service.dto.ActorSaveDto;
import com.sugarweb.digitalHuman.service.dto.ActorUpdateDto;
import com.sugarweb.digitalHuman.entity.Agent;
import com.sugarweb.digitalHuman.entity.Kb;
import com.sugarweb.digitalHuman.entity.Model;
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
    private KbService kbService;

    public Agent getById(String actorId) {
        Agent agent = Db.getById(actorId, Agent.class);
        if (StrUtil.isNotEmpty(agent.getChatModelId())) {
            Model model = Db.getById(actorId, Model.class);
            // actor.setChatModelInfo(model);
        }
        if (StrUtil.isNotEmpty(agent.getDatasetId())) {
            Kb kb = kbService.getById(agent.getDatasetId());
            // actor.setKbInfo(dataset);
        }
        return agent;
    }

    public Agent save(Agent agent) {
        Db.save(agent);
        return agent;
    }

    public Agent update(Agent agent) {
        Db.updateById(agent);
        return agent;
    }

    public IPage<ActorDetailDto> page(ActorPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<Agent>()
                .like(StrUtil.isNotEmpty(query.getActorName()), Agent::getAgentName, query.getActorName())
                .orderByDesc(Agent::getCreateTime)
        ).convert(this::buildActorDetailDto);
    }

    public ActorDetailDto detail(String actorId) {
        return buildActorDetailDto(getById(actorId));
    }

    public ActorDetailDto save(ActorSaveDto saveDto) {
        Agent agent = new Agent();
        agent.setAgentName(saveDto.getActorName());
        save(agent);
        return buildActorDetailDto(agent);
    }

    public ActorDetailDto update(ActorUpdateDto updateDto) {
        Agent agent = Db.getById(updateDto.getActorId(), Agent.class);
        if (agent != null) {
            agent.setAgentName(updateDto.getActorName());
            Db.updateById(agent);
        }
        return buildActorDetailDto(agent);
    }

    private ActorDetailDto buildActorDetailDto(Agent agent) {
        if (agent == null) {
            return null;
        }
        ActorDetailDto actorDetailDto = new ActorDetailDto();
        actorDetailDto.setActorId(agent.getAgentId());
        actorDetailDto.setActorName(agent.getAgentName());
        return actorDetailDto;
    }

    public void remove(String actorId) {
        Db.removeById(actorId, Agent.class);
    }
}
