package com.sugarweb.digitalHuman.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.AgentDetailDto;
import com.sugarweb.digitalHuman.application.dto.AgentPageQuery;
import com.sugarweb.digitalHuman.application.dto.AgentSaveDto;
import com.sugarweb.digitalHuman.application.dto.AgentUpdateDto;
import com.sugarweb.digitalHuman.domain.AgentInfo;
import com.sugarweb.digitalHuman.domain.KbInfo;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.digitalHuman.domain.PromptTemplateInfo;
import com.sugarweb.framework.orm.PageHelper;
import jakarta.annotation.Resource;
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

    @Resource
    private KbService kbService;
    @Resource
    private PromptService promptService;

    public AgentInfo getById(String agentId) {
        AgentInfo agentInfo = Db.getById(agentId, AgentInfo.class);
        if (StrUtil.isNotEmpty(agentInfo.getChatModelId())){
            ModelInfo modelInfo = Db.getById(agentId, ModelInfo.class);
            agentInfo.setChatModelInfo(modelInfo);
        }
        if (StrUtil.isNotEmpty(agentInfo.getKbId())){
            KbInfo kbInfo = kbService.getById(agentInfo.getKbId());
            agentInfo.setKbInfo(kbInfo);
        }
        if (StrUtil.isNotEmpty(agentInfo.getSystemPromptId())){
            PromptTemplateInfo promptTemplateInfo = promptService.getById(agentInfo.getSystemPromptId());
            agentInfo.setSystemPrompt(promptTemplateInfo);
        }
        return agentInfo;
    }

    public AgentInfo save(AgentInfo agentInfo) {
        Db.save(agentInfo);
        return agentInfo;
    }

    public AgentInfo update(AgentInfo agentInfo) {
        Db.updateById(agentInfo);
        return agentInfo;
    }

    public IPage<AgentDetailDto> page(AgentPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<AgentInfo>()
                .like(StrUtil.isNotEmpty(query.getAgentName()), AgentInfo::getAgentName, query.getAgentName())
                .orderByDesc(AgentInfo::getCreateTime)
        ).convert(this::buildAgentDetailDto);
    }

    public AgentDetailDto detail(String agentId) {
        return buildAgentDetailDto(getById(agentId));
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

    public void remove(String agentId) {
        Db.removeById(agentId, AgentInfo.class);
    }
}
