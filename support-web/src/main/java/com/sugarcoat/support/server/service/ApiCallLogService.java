package com.sugarcoat.support.server.service;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.support.server.domain.SgcApiCallLog;
import com.sugarcoat.support.server.service.dto.AccessLogQueryDto;

/**
 * 访问日志服务
 *
 * @author xxd
 * @since 2022-11-15
 */
public interface ApiCallLogService {

	SgcApiCallLog findOne(String id);

	PageData<SgcApiCallLog> findPage(PageDto pageDto, AccessLogQueryDto accessLogQueryDto);

}
