package com.sugarcoat.support.parameter.controller;

import com.sugarcoat.support.parameter.application.ParamDto;
import com.sugarcoat.support.parameter.application.ParamQueryDto;
import com.sugarcoat.support.parameter.application.ParamService;
import com.xxd.common.PageData;
import com.xxd.common.PageRequest;
import com.xxd.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 参数控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("param")
@RequiredArgsConstructor
public class ParameterController {

	private final ParamService paramService;

	@PostMapping("save")
	public Result<?> save(@RequestBody ParamDto dictDTO) {
		paramService.save(dictDTO);
		return Result.ok();
	}

	@PostMapping("reset/{ids}")
	public Result<?> reset(@PathVariable Set<String> ids) {
		paramService.reset(ids);
		return Result.ok();
	}

	@GetMapping("findOne/{id}")
	public Result<ParamDto> findOne(@PathVariable String id) {
		return Result.data(paramService.findById(id));
	}

	@GetMapping("findPage")
	public Result<PageData<ParamDto>> findPage(PageRequest pageRequest, ParamQueryDto queryVO) {
		return Result.data(paramService.findPage(pageRequest, queryVO));
	}

}
