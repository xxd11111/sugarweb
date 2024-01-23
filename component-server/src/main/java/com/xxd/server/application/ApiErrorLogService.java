package com.xxd.server.application;

import com.xxd.common.PageData;
import com.xxd.common.PageDto;
import com.xxd.server.domain.SgcApiErrorLog;
import com.xxd.server.application.dto.ApiErrorLogQueryDto;

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
