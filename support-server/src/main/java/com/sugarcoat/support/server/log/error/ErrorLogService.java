package com.sugarcoat.support.server.log.error;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;

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
