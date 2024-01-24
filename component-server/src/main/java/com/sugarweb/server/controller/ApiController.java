package com.sugarweb.server.controller;

import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.common.Result;
import com.sugarweb.server.application.SgcApiService;
import com.sugarweb.server.application.dto.SgcApiDto;
import com.sugarweb.server.application.dto.SgcApiQueryDto;
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

	private final SgcApiService sgcApiService;

	@Operation
	@GetMapping("findOne/{id}")
	public Result<SgcApiDto> findOne(@PathVariable String id) {
		return Result.data(sgcApiService.findOne(id));
	}

	@GetMapping("findPage")
	public Result<PageData<SgcApiDto>> findPage(PageRequest pageRequest, SgcApiQueryDto queryDto) {
		return Result.data(sgcApiService.findPage(pageRequest, queryDto));
	}

}
