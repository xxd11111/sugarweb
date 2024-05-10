package com.sugarweb.framework.security;

/**
 * 会话管理
 *
 * @author xxd
 * @version 1.0
 */
public interface RefreshTokenRepository {

    void save(RefreshToken accessTokenInfo);

    void update(RefreshToken accessTokenInfo);

    void delete(String accessToken);

    RefreshToken findOne(String accessToken);

}
