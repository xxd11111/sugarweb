package com.sugarcoat.protocol.server;

/**
 * TODO
 *
 * @author xxd
 * @since 2023/10/7 22:27
 */
public interface ApiService {

    ApiInfo getApiByUrl(String url);

    ApiInfo getApiById(String id);

    ApiInfo getCurrentApi();

}
