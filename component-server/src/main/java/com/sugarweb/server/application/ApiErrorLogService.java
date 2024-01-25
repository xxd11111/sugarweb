package com.sugarweb.server.application;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageRequest;
import com.sugarweb.server.domain.ApiErrorLog;
import com.sugarweb.server.application.dto.ApiErrorLogQueryDto;

/**
 * 异常日志服务
 *
 * @author xxd
 * @version 1.0
 */
public interface ApiErrorLogService {

	ApiErrorLog findOne(String id);

	PageData<ApiErrorLog> findPage(PageRequest pageRequest, ApiErrorLogQueryDto queryDto);

}
