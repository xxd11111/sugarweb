package com.sugarcoat.support.server.controller;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.support.server.service.dto.SgcApiDto;
import com.sugarcoat.support.server.service.dto.SgcApiQueryDto;
import com.sugarcoat.support.server.service.SgcApiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * serverApi接口
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/8
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
	public Result<PageData<SgcApiDto>> findPage(PageDto pageDto, SgcApiQueryDto queryDto) {
		return Result.data(sgcApiService.findPage(pageDto, queryDto));
	}

}
