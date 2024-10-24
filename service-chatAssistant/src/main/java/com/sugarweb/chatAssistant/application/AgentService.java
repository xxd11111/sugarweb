package com.sugarweb.chatAssistant.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.application.dto.AgentDetailDto;
import com.sugarweb.chatAssistant.application.dto.AgentPageQuery;
import com.sugarweb.chatAssistant.application.dto.AgentSaveDto;
import com.sugarweb.chatAssistant.application.dto.AgentUpdateDto;
import com.sugarweb.chatAssistant.domain.AgentInfo;
import com.sugarweb.framework.orm.PageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * AgentService
 *
 * @author xxd
 * @since 2024/10/15 22:40
 */
@Service
public class AgentService {

    public AgentInfo getAgentInfo(String agentId) {
        return Db.getById(agentId, AgentInfo.class);
    }

    public AgentInfo save(AgentInfo agentInfo) {
        Db.save(agentInfo);
        return agentInfo;
    }

    public AgentInfo update(AgentInfo agentInfo) {
        Db.updateById(agentInfo);
        return agentInfo;
    }

    public AgentInfo defaultAgentInfo() {
        AgentInfo agentInfo = getAgentInfo("1");
        if (agentInfo != null) {
            return agentInfo;
        } else {
            agentInfo = new AgentInfo();
            agentInfo.setAgentId("1");
            agentInfo.setAgentName("炫妹");
            agentInfo.setCreateTime(LocalDateTime.now());
            agentInfo.setUpdateTime(LocalDateTime.now());
            return save(agentInfo);
        }
    }

    public IPage<AgentDetailDto> page(AgentPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<AgentInfo>()
                .like(StrUtil.isNotEmpty(query.getAgentName()), AgentInfo::getAgentName, query.getAgentName())
                .orderByDesc(AgentInfo::getCreateTime)
        ).convert(this::buildAgentDetailDto);
    }

    public AgentDetailDto detail(String agentId) {
        return buildAgentDetailDto(getAgentInfo(agentId));
    }

    public AgentDetailDto save(AgentSaveDto saveDto) {
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentName(saveDto.getAgentName());
        agentInfo.setCreateTime(LocalDateTime.now());
        agentInfo.setUpdateTime(LocalDateTime.now());
        save(agentInfo);
        return buildAgentDetailDto(agentInfo);
    }

    public AgentDetailDto update(AgentUpdateDto updateDto) {
        AgentInfo agentInfo = Db.getById(updateDto.getAgentId(), AgentInfo.class);
        if (agentInfo != null) {
            agentInfo.setAgentName(updateDto.getAgentName());
            agentInfo.setUpdateTime(LocalDateTime.now());
            Db.updateById(agentInfo);
        }
        return buildAgentDetailDto(agentInfo);
    }

    private AgentDetailDto buildAgentDetailDto(AgentInfo agentInfo) {
        if (agentInfo == null) {
            return null;
        }
        AgentDetailDto agentDetailDto = new AgentDetailDto();
        agentDetailDto.setAgentId(agentInfo.getAgentId());
        agentDetailDto.setAgentName(agentInfo.getAgentName());
        agentDetailDto.setCreateTime(agentInfo.getCreateTime());
        agentDetailDto.setUpdateTime(agentInfo.getUpdateTime());
        return agentDetailDto;
    }
}
