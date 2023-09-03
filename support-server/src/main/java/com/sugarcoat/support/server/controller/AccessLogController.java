package com.sugarcoat.support.server.controller;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.support.server.application.AccessLogService;
import com.sugarcoat.support.server.domain.access.AccessLog;
import com.sugarcoat.support.server.application.dto.AccessLogQueryDto;
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
public class AccessLogController {

	private final AccessLogService accessLogService;

	@GetMapping("findOne")
	public AccessLog findOne(String id) {
		return accessLogService.findOne(id);
	}

	@GetMapping("findPage")
	public PageData<AccessLog> findPage(PageDto pageDto, AccessLogQueryDto queryDto) {
		return accessLogService.findPage(pageDto, queryDto);
	}

}
