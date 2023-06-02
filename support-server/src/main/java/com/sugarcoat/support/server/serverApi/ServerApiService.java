package com.sugarcoat.support.server.serverApi;


import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParameter;

/**
 * 接口服务
 * @author xxd
 * @version 1.0
 * @date 2023/3/13
 */
public interface ServerApiService {

    ServerApiDTO findOne(String id);

    PageData<ServerApiDTO> findPage(PageParameter pageParameter, ServerApiQueryVO queryVO);

}