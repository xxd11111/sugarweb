package com.sugarweb.param.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.param.application.ParamDto;
import com.sugarweb.param.application.ParamQuery;
import com.sugarweb.param.application.ParamService;
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
public class ParamController {

    private final ParamService paramService;

    @PostMapping("save")
    public R<Void> save(@RequestBody ParamDto paramDto) {
        paramService.save(paramDto);
        return R.ok();
    }

    @PostMapping("update")
    public R<Void> update(@RequestBody ParamDto paramDto) {
        paramService.update(paramDto);
        return R.ok();
    }

    @GetMapping("getById/{id}")
    public R<ParamDto> getById(@PathVariable String id) {
        return R.data(paramService.getById(id));
    }

    @GetMapping("getByCode/{code}")
    public R<ParamDto> getByCode(@PathVariable String code) {
        return R.data(paramService.getByCode(code));
    }

    @GetMapping("page")
    public R<IPage<ParamDto>> findPage(PageQuery pageQuery, ParamQuery query) {
        return R.data(paramService.page(pageQuery, query));
    }

}
