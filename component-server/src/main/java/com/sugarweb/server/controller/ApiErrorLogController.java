package com.sugarweb.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.server.domain.ApiErrorLog;
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

	@GetMapping("detail")
	public ApiErrorLog findOne(String id) {
		return apiErrorLogService.findOne(id);
	}

	@GetMapping("page")
	public R<IPage<ApiErrorLog>> findPage(PageQuery pageQuery, ApiErrorLogQueryDto queryDto) {
		return R.data(apiErrorLogService.findPage(pageQuery, queryDto));
	}

}
