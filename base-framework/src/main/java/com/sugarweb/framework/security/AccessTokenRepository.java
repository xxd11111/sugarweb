package com.sugarweb.framework.security;

/**
 * 会话管理
 *
 * @author xxd
 * @version 1.0
 */
public interface AccessTokenRepository {

    void save(AccessToken accessToken);

    void update(AccessToken accessToken);

    void delete(String accessToken);

    AccessToken findOne(String accessToken);

}
