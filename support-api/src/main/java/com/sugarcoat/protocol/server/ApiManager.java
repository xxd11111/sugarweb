package com.sugarcoat.protocol.server;

import java.util.Collection;
import java.util.Optional;

/**
 * api服务
 *
 * @author xxd
 * @since 2023/10/7 22:27
 */
public interface ApiManager {

    Optional<ApiInfo> findApiByUrl(String url);

    Optional<ApiInfo> findApiById(String id);

    Optional<ApiInfo> findCurrentApi();

    void save(ApiInfo apiInfo);

    void remove(String id);

    Collection<ApiInfo> findAll();

}
