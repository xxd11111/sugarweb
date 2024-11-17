package com.sugarweb.digitalHuman.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.digitalHuman.application.ScriptService;
import com.sugarweb.digitalHuman.application.dto.ScriptDetailDto;
import com.sugarweb.digitalHuman.application.dto.ScriptPageQuery;
import com.sugarweb.digitalHuman.application.dto.ScriptSaveDto;
import com.sugarweb.digitalHuman.application.dto.ScriptUpdateDto;
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
@RequestMapping("/script")
@Tag(name = "场景管理")
public class ScriptController {

    @Resource
    private ScriptService scriptService;

    @GetMapping("/page")
    @Operation(operationId = "script:page", summary = "分页查询场景列表")
    public R<IPage<ScriptDetailDto>> page(ScriptPageQuery query) {
        return R.data(scriptService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "script:detail", summary = "查询场景详情")
    public R<ScriptDetailDto> detail(String scriptId) {
        return R.data(scriptService.detail(scriptId));
    }

    @PostMapping("/save")
    @Operation(operationId = "script:save", summary = "新增场景")
    public R<ScriptDetailDto> save(ScriptSaveDto saveDto) {
        return R.data(scriptService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "script:update", summary = "更新场景")
    public R<ScriptDetailDto> update(ScriptUpdateDto updateDto) {
        return R.data(scriptService.update(updateDto));
    }

    @PostMapping("/remove")
    @Operation(operationId = "script:remove", summary = "删除场景")
    public R<Void> remove(String scriptId) {
        scriptService.remove(scriptId);
        return R.ok();
    }


}
