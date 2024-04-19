package com.sugarweb.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.server.domain.ApiCallLog;
import com.sugarweb.server.application.dto.ApiCallLogQueryDto;
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
	public R<ApiCallLog> findOne(String id) {
		return R.data(apiCallLogService.findOne(id));
	}

	@GetMapping("findPage")
	public R<IPage<ApiCallLog>> findPage(PageQuery pageQuery, ApiCallLogQueryDto queryDto) {
		return R.data(apiCallLogService.findPage(pageQuery, queryDto));
	}

}
