package com.sugarcoat.support.server.service;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.support.server.domain.SgcApiErrorLog;
import com.sugarcoat.support.server.service.dto.ErrorLogQueryDto;

/**
 * 异常日志服务
 *
 * @author xxd
 * @since 2022-11-15
 */
public interface ErrorLogService {

	SgcApiErrorLog findOne(String id);

	PageData<SgcApiErrorLog> findPage(PageDto pageDto, ErrorLogQueryDto queryDto);

}
