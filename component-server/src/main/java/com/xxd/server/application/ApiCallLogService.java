package com.xxd.server.application;

import com.xxd.common.PageData;
import com.xxd.common.PageDto;
import com.xxd.server.domain.SgcApiCallLog;
import com.xxd.server.application.dto.AccessLogQueryDto;

/**
 * 访问日志服务
 *
 * @author xxd
 * @version 1.0
 */
public interface ApiCallLogService {

	SgcApiCallLog findOne(String id);

	PageData<SgcApiCallLog> findPage(PageDto pageDto, AccessLogQueryDto accessLogQueryDto);

}
