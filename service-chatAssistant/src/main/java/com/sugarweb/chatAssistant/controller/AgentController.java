package com.sugarweb.chatAssistant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.chatAssistant.application.AgentService;
import com.sugarweb.chatAssistant.application.dto.AgentDetailDto;
import com.sugarweb.chatAssistant.application.dto.AgentPageQuery;
import com.sugarweb.chatAssistant.application.dto.AgentSaveDto;
import com.sugarweb.chatAssistant.application.dto.AgentUpdateDto;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * agent管理
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/agent")
@Tag(name = "agent管理")
public class AgentController {

    @Resource
    private AgentService agentService;

    @GetMapping("/page")
    @Operation(operationId = "agent:page", summary = "分页查询代理列表")
    public R<IPage<AgentDetailDto>> page(AgentPageQuery query) {
        return R.data(agentService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "agent:detail", summary = "查询代理详情")
    public R<AgentDetailDto> detail(String agentId) {
        return R.data(agentService.detail(agentId));
    }

    @PostMapping("/save")
    @Operation(operationId = "agent:save", summary = "新增代理")
    public R<AgentDetailDto> save(AgentSaveDto saveDto) {
        return R.data(agentService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "agent:update", summary = "更新代理")
    public R<AgentDetailDto> update(AgentUpdateDto updateDto) {
        return R.data(agentService.update(updateDto));
    }

}
