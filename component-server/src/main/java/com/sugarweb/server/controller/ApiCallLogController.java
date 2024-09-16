package com.sugarweb.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.server.po.ApiCallLog;
import com.sugarweb.server.application.dto.ApiCallLogQuery;
import com.sugarweb.server.application.ApiCallLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("getOne")
	public R<ApiCallLog> getOne(@RequestParam String id) {
		return R.data(apiCallLogService.getOne(id));
	}

	@GetMapping("page")
	public R<IPage<ApiCallLog>> page(PageQuery pageQuery, ApiCallLogQuery queryDto) {
		return R.data(apiCallLogService.page(pageQuery, queryDto));
	}

}
