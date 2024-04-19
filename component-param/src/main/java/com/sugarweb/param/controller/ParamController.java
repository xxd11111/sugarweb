package com.sugarweb.param.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.param.application.ParamDto;
import com.sugarweb.param.application.ParamQueryDto;
import com.sugarweb.param.application.ParamService;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
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
    public R<?> save(@RequestBody ParamDto paramDto) {
        paramService.save(paramDto);
        return R.ok();
    }

    @GetMapping("findOne/{id}")
    public R<ParamDto> findOne(@PathVariable String id) {
        return R.data(paramService.findById(id).orElse(null));
    }

    @GetMapping("findPage")
    public R<IPage<ParamDto>> findPage(PageQuery pageQuery, ParamQueryDto queryVO) {
        return R.data(paramService.findPage(pageQuery, queryVO));
    }

}
