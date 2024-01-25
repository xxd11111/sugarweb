package com.sugarweb.server.controller;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageRequest;
import com.sugarweb.server.domain.ApiCallLog;
import com.sugarweb.server.application.dto.AccessLogQueryDto;
import com.sugarweb.server.application.ApiCallLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问日志控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("/server/accessLog")
@RequiredArgsConstructor
public class ApiCallLogController {

	private final ApiCallLogService apiCallLogService;

	@GetMapping("findOne")
	public ApiCallLog findOne(String id) {
		return apiCallLogService.findOne(id);
	}

	@GetMapping("findPage")
	public PageData<ApiCallLog> findPage(PageRequest pageRequest, AccessLogQueryDto queryDto) {
		return apiCallLogService.findPage(pageRequest, queryDto);
	}

}
