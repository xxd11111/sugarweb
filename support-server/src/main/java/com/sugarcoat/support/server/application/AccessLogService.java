package com.sugarcoat.support.server.application;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.support.server.domain.access.AccessLog;
import com.sugarcoat.support.server.application.dto.AccessLogQueryDto;

/**
 * 访问日志服务
 *
 * @author xxd
 * @date 2022-11-15
 */
public interface AccessLogService {

	AccessLog findOne(String id);

	PageData<AccessLog> findPage(PageDto pageDto, AccessLogQueryDto accessLogQueryDto);

}
