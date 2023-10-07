package com.sugarcoat.support.server.controller;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.support.server.service.ApiCallLogService;
import com.sugarcoat.support.server.domain.SgcApiCallLog;
import com.sugarcoat.support.server.service.dto.AccessLogQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问日志控制器
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/2
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
	public PageData<SgcApiCallLog> findPage(PageDto pageDto, AccessLogQueryDto queryDto) {
		return apiCallLogService.findPage(pageDto, queryDto);
	}

}