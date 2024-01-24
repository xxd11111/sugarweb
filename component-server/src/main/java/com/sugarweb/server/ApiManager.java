package com.sugarweb.server;

import java.util.Collection;
import java.util.Optional;

/**
 * api服务
 *
 * @author xxd
 * @version 1.0
 */
public interface ApiManager {

    Optional<ApiInfo> findApiByUrl(String url);

    Optional<ApiInfo> findApiById(String id);

    Optional<ApiInfo> findCurrentApi();

    void save(ApiInfo apiInfo);

    void remove(String id);

    Collection<ApiInfo> findAll();

}
