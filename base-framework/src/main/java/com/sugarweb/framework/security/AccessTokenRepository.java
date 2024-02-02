package com.sugarweb.framework.security;

/**
 * 会话管理
 *
 * @author xxd
 * @version 1.0
 */
public interface AccessTokenRepository {

    void save(AccessTokenInfo accessTokenInfo);

    void update(AccessTokenInfo accessTokenInfo);

    void delete(String accessToken);

    AccessTokenInfo findOne(String accessToken);

}
