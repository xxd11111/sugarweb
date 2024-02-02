package com.sugarweb.framework.security;

/**
 * 会话管理
 *
 * @author xxd
 * @version 1.0
 */
public interface RefreshTokenRepository {

    void save(RefreshTokenInfo accessTokenInfo);

    void update(RefreshTokenInfo accessTokenInfo);

    void delete(String accessToken);

    RefreshTokenInfo findOne(String accessToken);

}
