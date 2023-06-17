package com.sugarcoat.support.server.log.access;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问日志控制器
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/2
 */
@RestController
@RequestMapping("accessLog")
@RequiredArgsConstructor
public class AccessLogController {

	private final AccessLogService accessLogService;

	@GetMapping("findOne")
	public AccessLog findOne(String id) {
		return accessLogService.findOne(id);
	}

	@GetMapping("findPage")
	public PageData<AccessLog> findPage(PageParameter pageParameter, AccessLogQueryCmd queryCmd) {
		return accessLogService.findPage(pageParameter, queryCmd);
	}

}
