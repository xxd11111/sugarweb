package com.sugarweb.server.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.server.domain.ApiErrorLog;
import com.sugarweb.server.application.dto.ApiErrorLogQueryDto;

/**
 * 异常日志服务
 *
 * @author xxd
 * @version 1.0
 */
public interface ApiErrorLogService {

	ApiErrorLog findOne(String id);

	IPage<ApiErrorLog> findPage(PageQuery pageQuery, ApiErrorLogQueryDto queryDto);

}
