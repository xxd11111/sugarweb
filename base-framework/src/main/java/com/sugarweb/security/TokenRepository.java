package com.sugarweb.security;

/**
 * 会话管理
 *
 * @author xxd
 * @version 1.0
 */
public interface TokenRepository {

    void save(TokenInfo tokenInfo);

    void update(TokenInfo tokenInfo);

    void delete(String accessToken);

    TokenInfo findOne(String accessToken);

    TokenInfo findRefreshToken(String refreshToken);

}
