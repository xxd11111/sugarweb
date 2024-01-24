package com.sugarweb.server.application;

import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.server.domain.SgcApiErrorLog;
import com.sugarweb.server.application.dto.ApiErrorLogQueryDto;

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
