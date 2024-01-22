package com.xxd.server.controller;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.xxd.server.domain.SgcApiErrorLog;
import com.xxd.server.application.ApiErrorLogService;
import com.xxd.server.application.dto.ApiErrorLogQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常日志控制器
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/2
 */
@RestController
@RequestMapping("/server/errorLog")
@RequiredArgsConstructor
public class ApiErrorLogController {

	private final ApiErrorLogService apiErrorLogService;

	@GetMapping("findOne")
	public SgcApiErrorLog findOne(String id) {
		return apiErrorLogService.findOne(id);
	}

	@GetMapping("findPage")
	public PageData<SgcApiErrorLog> findPage(PageDto pageDto, ApiErrorLogQueryDto queryDto) {
		return apiErrorLogService.findPage(pageDto, queryDto);
	}

}
