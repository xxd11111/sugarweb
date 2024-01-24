package com.sugarweb.server.controller;

import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.server.domain.SgcApiErrorLog;
import com.sugarweb.server.application.ApiErrorLogService;
import com.sugarweb.server.application.dto.ApiErrorLogQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常日志控制器
 *
 * @author xxd
 * @version 1.0
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
	public PageData<SgcApiErrorLog> findPage(PageRequest pageRequest, ApiErrorLogQueryDto queryDto) {
		return apiErrorLogService.findPage(pageRequest, queryDto);
	}

}
