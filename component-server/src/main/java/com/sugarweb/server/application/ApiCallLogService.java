package com.sugarweb.server.application;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageRequest;
import com.sugarweb.server.domain.ApiCallLog;
import com.sugarweb.server.application.dto.AccessLogQueryDto;

/**
 * 访问日志服务
 *
 * @author xxd
 * @version 1.0
 */
public interface ApiCallLogService {

	ApiCallLog findOne(String id);

	PageData<ApiCallLog> findPage(PageRequest pageRequest, AccessLogQueryDto accessLogQueryDto);

}
