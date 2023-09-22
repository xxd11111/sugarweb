package com.sugarcoat.support.server.service;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.support.server.service.dto.ServerApiDto;
import com.sugarcoat.support.server.service.dto.ServerApiQueryDto;

/**
 * 接口服务
 *
 * @author xxd
 * @version 1.0
 * @since 2023/3/13
 */
public interface ServerApiService {

	ServerApiDto findOne(String id);

	PageData<ServerApiDto> findPage(PageDto pageDto, ServerApiQueryDto queryVO);

}