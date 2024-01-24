package com.xxd.server.application;

import com.xxd.common.PageData;
import com.xxd.common.PageRequest;
import com.xxd.server.domain.SgcApiErrorLog;
import com.xxd.server.application.dto.ApiErrorLogQueryDto;

/**
 * 异常日志服务
 *
 * @author xxd
 * @version 1.0
 */
public interface ApiErrorLogService {

	SgcApiErrorLog findOne(String id);

	PageData<SgcApiErrorLog> findPage(PageRequest pageRequest, ApiErrorLogQueryDto queryDto);

}
