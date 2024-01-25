package com.sugarweb.param.controller;

import com.sugarweb.param.application.ParamDto;
import com.sugarweb.param.application.ParamQueryDto;
import com.sugarweb.param.application.ParamService;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageRequest;
import com.sugarweb.framework.common.Result;
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
public class ParamController {

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
