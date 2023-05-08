package com.xxd.sugarcoat.support.server.api;

import com.xxd.sugarcoat.support.servelt.protocol.PageData;
import com.xxd.sugarcoat.support.servelt.protocol.PageParam;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/13
 */
public interface ServerApiService {

    void save(ServerApiDTO serverApiDTO);

    void remove(String id);

    ServerApiDTO findOne(String id);

    PageData<ServerApiDTO> findPage(PageParam pageParam, ServerApiQueryVO queryVO);

}