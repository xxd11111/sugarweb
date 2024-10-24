package com.sugarweb.chatAssistant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.chatAssistant.application.StageService;
import com.sugarweb.chatAssistant.application.dto.StageDetailDto;
import com.sugarweb.chatAssistant.application.dto.StagePageQuery;
import com.sugarweb.chatAssistant.application.dto.StageSaveDto;
import com.sugarweb.chatAssistant.application.dto.StageUpdateDto;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 舞台管理
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/stage")
@Tag(name = "舞台管理")
public class StageController {

    @Resource
    private StageService stageService;

    @GetMapping("/page")
    @Operation(operationId = "stage:page", summary = "分页查询")
    public R<IPage<StageDetailDto>> page(StagePageQuery query) {
        return R.data(stageService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "stage:detail", summary = "详情")
    public R<StageDetailDto> detail(String stageId) {
        return R.data(stageService.detail(stageId));
    }

    @PostMapping("/save")
    @Operation(operationId = "stage:save", summary = "新增")
    public R<StageDetailDto> save(StageSaveDto saveDto) {
        return R.data(stageService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "stage:update", summary = "更新")
    public R<StageDetailDto> update(StageUpdateDto updateDto) {
        return R.data(stageService.update(updateDto));
    }

}
