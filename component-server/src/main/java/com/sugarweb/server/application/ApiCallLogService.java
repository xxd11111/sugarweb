package com.sugarweb.server.application;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.server.domain.ApiCallLog;
import com.sugarweb.server.application.dto.ApiCallLogQueryDto;

/**
 * 访问日志服务
 *
 * @author xxd
 * @version 1.0
 */
public interface ApiCallLogService {

	ApiCallLog findOne(String id);

	PageData<ApiCallLog> findPage(PageQuery pageQuery, ApiCallLogQueryDto apiCallLogQueryDto);

}
