package com.sugarcoat.support.server.api;


import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParam;

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