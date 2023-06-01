package com.sugarcoat.support.server.log;

import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParameter;

/**
 * 异常日志服务
 *
 * @author xxd
 * @date 2022-11-15
 */
public interface ErrorLogService {

    ErrorLog findOne(String id);

    PageData<ErrorLog> findPage(PageParameter pageParameter, ErrorLogQueryCmd errorLogQueryCmd);

}
