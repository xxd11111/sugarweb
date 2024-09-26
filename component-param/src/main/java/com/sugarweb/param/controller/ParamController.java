package com.sugarweb.param.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.param.application.ParamDto;
import com.sugarweb.param.application.ParamQuery;
import com.sugarweb.param.application.ParamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 参数控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("param")
@RequiredArgsConstructor
@Tag(name = "参数管理")
public class ParamController {

    private final ParamService paramService;

    @PostMapping("save")
    @Operation(operationId = "param:save", summary = "新增")
    public R<Void> save(@RequestBody ParamDto paramDto) {
        paramService.save(paramDto);
        return R.ok();
    }

    @PostMapping("update")
    @Operation(operationId = "param:update", summary = "更新")
    public R<Void> update(@RequestBody ParamDto paramDto) {
        paramService.update(paramDto);
        return R.ok();
    }

    @GetMapping("getById")
    @Operation(operationId = "param:getById", summary = "根据id获取详情")
    public R<ParamDto> getById(@RequestParam String id) {
        return R.data(paramService.getById(id));
    }

    @GetMapping("getByCode")
    @Operation(operationId = "param:getByCode", summary = "根据code获取详情")
    public R<ParamDto> getByCode(@RequestParam String code) {
        return R.data(paramService.getByCode(code));
    }

    @GetMapping("page")
    @Operation(operationId = "param:page", summary = "分页")
    public R<IPage<ParamDto>> findPage(PageQuery pageQuery, ParamQuery query) {
        return R.data(paramService.page(pageQuery, query));
    }

}
