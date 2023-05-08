package com.xxd.sugarcoat.support.param;

import com.xxd.sugarcoat.support.param.api.ParamDTO;
import com.xxd.sugarcoat.support.param.api.ParamQueryVO;
import com.xxd.sugarcoat.support.param.api.ParamService;
import com.xxd.sugarcoat.support.servelt.protocol.PageData;
import com.xxd.sugarcoat.support.servelt.protocol.PageParam;
import com.xxd.sugarcoat.support.servelt.protocol.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author xxd
 * @description TODO
 * @date 2023/5/7 0:41
 */
@RestController
@RequestMapping("param")
@RequiredArgsConstructor
public class ParamController {
    private final ParamService paramService;

    @PostMapping("save")
    public Result<?> save(@RequestBody ParamDTO dictDTO) {
        paramService.save(dictDTO);
        return Result.ok();
    }

    @DeleteMapping("remove/{ids}")
    public Result<?> remove(@PathVariable Set<String> ids) {
        paramService.remove(ids);
        return Result.ok();
    }

    @PostMapping("reset/{ids}")
    public Result<?> reset(@PathVariable Set<String> ids) {
        paramService.reset(ids);
        return Result.ok();
    }

    @GetMapping("findOne/{id}")
    public Result<ParamDTO> findOne(@PathVariable String id) {
        return Result.data(paramService.findById(id));
    }

    @GetMapping("findPage")
    public Result<PageData<ParamDTO>> findPage(PageParam pageParam, ParamQueryVO queryVO) {
        return Result.data(paramService.findPage(pageParam, queryVO));
    }
}
