package com.sugarcoat.support.server.log.error;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常日志控制器
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/2
 */
@RestController
@RequestMapping("errorLog")
@RequiredArgsConstructor
public class ErrorLogController {

	private final ErrorLogService errorLogService;

	@GetMapping("findOne")
	public ErrorLog findOne(String id) {
		return errorLogService.findOne(id);
	}

	@GetMapping("findPage")
	public PageData<ErrorLog> findPage(PageParameter pageParameter, ErrorLogQueryCmd queryCmd) {
		return errorLogService.findPage(pageParameter, queryCmd);
	}

}
