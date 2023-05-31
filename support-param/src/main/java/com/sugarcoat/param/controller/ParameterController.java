package com.sugarcoat.param.controller;

import com.sugarcoat.param.application.ParameterDTO;
import com.sugarcoat.param.application.ParamQueryCmd;
import com.sugarcoat.param.application.ParameterService;
import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParam;
import com.sugarcoat.protocol.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 参数控制器
 *
 * @author xxd
 * @date 2023/5/7 0:41
 */
@RestController
@RequestMapping("param")
@RequiredArgsConstructor
public class ParameterController {
    private final ParameterService parameterService;

    @PostMapping("save")
    public Result<?> save(@RequestBody ParameterDTO dictDTO) {
        parameterService.save(dictDTO);
        return Result.ok();
    }

    @DeleteMapping("remove/{ids}")
    public Result<?> remove(@PathVariable Set<String> ids) {
        parameterService.remove(ids);
        return Result.ok();
    }

    @PostMapping("reset/{ids}")
    public Result<?> reset(@PathVariable Set<String> ids) {
        parameterService.reset(ids);
        return Result.ok();
    }

    @GetMapping("findOne/{id}")
    public Result<ParameterDTO> findOne(@PathVariable String id) {
        return Result.data(parameterService.findById(id));
    }

    @GetMapping("findPage")
    public Result<PageData<ParameterDTO>> findPage(PageParam pageParam, ParamQueryCmd queryVO) {
        return Result.data(parameterService.findPage(pageParam, queryVO));
    }
}
