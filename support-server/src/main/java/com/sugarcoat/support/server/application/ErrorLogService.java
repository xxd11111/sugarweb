package com.sugarcoat.support.server.application;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.support.server.domain.error.ErrorLog;
import com.sugarcoat.support.server.application.dto.ErrorLogQueryDto;

/**
 * 异常日志服务
 *
 * @author xxd
 * @date 2022-11-15
 */
public interface ErrorLogService {

	ErrorLog findOne(String id);

	PageData<ErrorLog> findPage(PageDto pageDto, ErrorLogQueryDto queryDto);

}
