package com.sugarweb.chatAssistant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.chatAssistant.application.SceneService;
import com.sugarweb.chatAssistant.application.dto.SceneDetailDto;
import com.sugarweb.chatAssistant.application.dto.ScenePageQuery;
import com.sugarweb.chatAssistant.application.dto.SceneSaveDto;
import com.sugarweb.chatAssistant.application.dto.SceneUpdateDto;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 场景管理
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/scene")
@Tag(name = "场景管理")
public class SceneController {

    @Resource
    private SceneService sceneService;

    @GetMapping("/page")
    @Operation(operationId = "scene:page", summary = "分页查询场景列表")
    public R<IPage<SceneDetailDto>> page(ScenePageQuery query) {
        return R.data(sceneService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "scene:detail", summary = "查询场景详情")
    public R<SceneDetailDto> detail(String sceneId) {
        return R.data(sceneService.detail(sceneId));
    }

    @PostMapping("/save")
    @Operation(operationId = "scene:save", summary = "新增场景")
    public R<SceneDetailDto> save(SceneSaveDto saveDto) {
        return R.data(sceneService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "scene:update", summary = "更新场景")
    public R<SceneDetailDto> update(SceneUpdateDto updateDto) {
        return R.data(sceneService.update(updateDto));
    }

}
