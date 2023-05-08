package com.xxd.sugarcoat.support.dev.server;

import com.xxd.sugarcoat.support.servelt.protocol.PageData;
import com.xxd.sugarcoat.support.servelt.protocol.PageParam;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/13
 */
public interface ServerApiService {

    void save(ServerApi serverApi);

    void remove(String id);

    ServerApi findOne(String id);

    PageData<ServerApi> findPage(PageParam pageParam, ServerApiQueryVO queryVO);

    void refresh();

}