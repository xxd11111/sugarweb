package com.sugarweb.digitalHuman.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.service.dto.AgentDetailDto;
import com.sugarweb.digitalHuman.service.dto.AgentPageQuery;
import com.sugarweb.digitalHuman.service.dto.ActorSaveDto;
import com.sugarweb.digitalHuman.service.dto.AgentUpdateDto;
import com.sugarweb.digitalHuman.entity.Agent;
import com.sugarweb.framework.orm.PageHelper;
import org.springframework.stereotype.Service;

/**
 * ActorService
 *
 * @author xxd
 * @since 2024/10/15 22:40
 */
@Service
public class AgentService {

    public Agent getById(String actorId) {
        Agent agent = Db.getById(actorId, Agent.class);
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

    public IPage<AgentDetailDto> page(AgentPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<Agent>()
                .like(StrUtil.isNotEmpty(query.getActorName()), Agent::getAgentName, query.getActorName())
                .orderByDesc(Agent::getCreateTime)
        ).convert(this::buildActorDetailDto);
    }

    public AgentDetailDto detail(String actorId) {
        return buildActorDetailDto(getById(actorId));
    }

    public AgentDetailDto save(ActorSaveDto saveDto) {
        Agent agent = new Agent();
        agent.setAgentName(saveDto.getAgentName());
        save(agent);
        return buildActorDetailDto(agent);
    }

    public AgentDetailDto update(AgentUpdateDto updateDto) {
        Agent agent = Db.getById(updateDto.getAgentId(), Agent.class);
        if (agent != null) {
            agent.setAgentName(updateDto.getAgentName());
            Db.updateById(agent);
        }
        return buildActorDetailDto(agent);
    }

    private AgentDetailDto buildActorDetailDto(Agent agent) {
        if (agent == null) {
            return null;
        }
        AgentDetailDto agentDetailDto = new AgentDetailDto();
        agentDetailDto.setActorId(agent.getAgentId());
        agentDetailDto.setActorName(agent.getAgentName());
        return agentDetailDto;
    }

    public void remove(String actorId) {
        Db.removeById(actorId, Agent.class);
    }
}
