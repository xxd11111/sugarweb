package com.sugarweb.digitalHuman.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.digitalHuman.service.AgentService;
import com.sugarweb.digitalHuman.service.dto.AgentDetailDto;
import com.sugarweb.digitalHuman.service.dto.AgentPageQuery;
import com.sugarweb.digitalHuman.service.dto.ActorSaveDto;
import com.sugarweb.digitalHuman.service.dto.AgentUpdateDto;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 智能体管理
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/agent")
@Tag(name = "智能体管理")
public class AgentController {

    @Resource
    private AgentService agentService;

    @GetMapping("/page")
    @Operation(operationId = "agent:page", summary = "分页查询智能体列表")
    public R<IPage<AgentDetailDto>> page(AgentPageQuery query) {
        return R.data(agentService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "agent:detail", summary = "查询智能体详情")
    public R<AgentDetailDto> detail(String actorId) {
        return R.data(agentService.detail(actorId));
    }

    @PostMapping("/save")
    @Operation(operationId = "agent:save", summary = "新增智能体")
    public R<AgentDetailDto> save(ActorSaveDto saveDto) {
        return R.data(agentService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "agent:update", summary = "更新智能体")
    public R<AgentDetailDto> update(AgentUpdateDto updateDto) {
        return R.data(agentService.update(updateDto));
    }

    @PostMapping("/remove")
    @Operation(operationId = "agent:remove", summary = "删除智能体")
    public R<Void> remove(String actorId) {
        agentService.remove(actorId);
        return R.ok();
    }

}
