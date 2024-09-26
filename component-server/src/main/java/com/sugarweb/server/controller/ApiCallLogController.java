package com.sugarweb.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.server.domain.po.ApiCallLog;
import com.sugarweb.server.application.dto.ApiCallLogQuery;
import com.sugarweb.server.application.ApiCallLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/apiCallLog")
@RequiredArgsConstructor
@Tag(name = "接口调用日志")
public class ApiCallLogController {

	private final ApiCallLogService apiCallLogService;

	@GetMapping("getById")
	@Operation(operationId = "apiCallLog:getById", summary = "详情")
	public R<ApiCallLog> getById(@RequestParam String id) {
		return R.data(apiCallLogService.getById(id));
	}

	@GetMapping("page")
	@Operation(operationId = "apiCallLog:page", summary = "分页")
	public R<IPage<ApiCallLog>> page(PageQuery pageQuery, ApiCallLogQuery queryDto) {
		return R.data(apiCallLogService.page(pageQuery, queryDto));
	}

}
