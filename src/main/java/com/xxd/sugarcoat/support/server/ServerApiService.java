package com.xxd.sugarcoat.support.server;

import java.util.Set;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/13
 */
public interface ServerApiService {

    Set<ServerApi> scanApi();

    void refreshApi(Set<ServerApi> serverApis);

}