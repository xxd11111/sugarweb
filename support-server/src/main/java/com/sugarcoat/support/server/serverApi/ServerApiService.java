package com.sugarcoat.support.server.serverApi;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;

/**
 * 接口服务
 *
 * @author xxd
 * @version 1.0
 * @date 2023/3/13
 */
public interface ServerApiService {

	ServerApiDto findOne(String id);

	PageData<ServerApiDto> findPage(PageDto pageDto, ServerApiQueryVo queryVO);

}