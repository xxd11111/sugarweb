package com.sugarcoat.support.server.controller;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.support.server.service.ErrorLogService;
import com.sugarcoat.support.server.domain.SgcApiErrorLog;
import com.sugarcoat.support.server.service.dto.ErrorLogQueryDto;
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
public class ErrorLogController {

	private final ErrorLogService errorLogService;

	@GetMapping("findOne")
	public SgcApiErrorLog findOne(String id) {
		return errorLogService.findOne(id);
	}

	@GetMapping("findPage")
	public PageData<SgcApiErrorLog> findPage(PageDto pageDto, ErrorLogQueryDto queryDto) {
		return errorLogService.findPage(pageDto, queryDto);
	}

}
