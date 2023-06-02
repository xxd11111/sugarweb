package com.sugarcoat.support.server.log.access;

import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParameter;

/**
 * 访问日志服务
 *
 * @author xxd
 * @date 2022-11-15
 */
public interface AccessLogService {

    AccessLog findOne(String id);

    PageData<AccessLog> findPage(PageParameter pageParameter, AccessLogQueryCmd accessLogQueryCmd);

}
