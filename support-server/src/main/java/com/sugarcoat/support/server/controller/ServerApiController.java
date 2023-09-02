package com.sugarcoat.support.server.controller;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.api.common.Result;
import com.sugarcoat.support.server.application.dto.ServerApiDto;
import com.sugarcoat.support.server.application.dto.ServerApiQueryDto;
import com.sugarcoat.support.server.application.ServerApiService;
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
public class ServerApiController {

	private final ServerApiService serverApiService;

	@GetMapping("findOne/{id}")
	public Result<ServerApiDto> findOne(@PathVariable String id) {
		return Result.data(serverApiService.findOne(id));
	}

	@GetMapping("findPage")
	public Result<PageData<ServerApiDto>> findPage(PageDto pageDto, ServerApiQueryDto queryVO) {
		return Result.data(serverApiService.findPage(pageDto, queryVO));
	}

}
