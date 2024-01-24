package com.xxd.server.controller;

import com.xxd.common.PageData;
import com.xxd.common.PageRequest;
import com.xxd.server.domain.SgcApiCallLog;
import com.xxd.server.application.dto.AccessLogQueryDto;
import com.xxd.server.application.ApiCallLogService;
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
	public SgcApiCallLog findOne(String id) {
		return apiCallLogService.findOne(id);
	}

	@GetMapping("findPage")
	public PageData<SgcApiCallLog> findPage(PageRequest pageRequest, AccessLogQueryDto queryDto) {
		return apiCallLogService.findPage(pageRequest, queryDto);
	}

}
