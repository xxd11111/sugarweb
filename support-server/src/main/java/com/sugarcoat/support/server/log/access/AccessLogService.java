package com.sugarcoat.support.server.log.access;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;

/**
 * 访问日志服务
 *
 * @author xxd
 * @date 2022-11-15
 */
public interface AccessLogService {

	AccessLog findOne(String id);

	PageData<AccessLog> findPage(PageDto pageDto, AccessLogQueryCmd accessLogQueryCmd);

}
