package com.sugarweb.server.controller;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.Result;
import com.sugarweb.server.application.ApiService;
import com.sugarweb.server.application.dto.ApiInfoDto;
import com.sugarweb.server.application.dto.ApiInfoQueryDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * serverApi接口
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("/server/serverApi")
@RequiredArgsConstructor
public class ApiController {

	private final ApiService apiService;

	@Operation
	@GetMapping("findOne/{id}")
	public Result<ApiInfoDto> findOne(@PathVariable String id) {
		return Result.data(apiService.findOne(id));
	}

	@GetMapping("findPage")
	public Result<PageData<ApiInfoDto>> findPage(PageQuery pageQuery, ApiInfoQueryDto queryDto) {
		return Result.data(apiService.findPage(pageQuery, queryDto));
	}

}
