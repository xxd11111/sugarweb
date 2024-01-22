package com.xxd.server.service;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.xxd.server.domain.SgcApiErrorLog;
import com.xxd.server.service.dto.ApiErrorLogQueryDto;

/**
 * 异常日志服务
 *
 * @author xxd
 * @since 2022-11-15
 */
public interface ApiErrorLogService {

	SgcApiErrorLog findOne(String id);

	PageData<SgcApiErrorLog> findPage(PageDto pageDto, ApiErrorLogQueryDto queryDto);

}
