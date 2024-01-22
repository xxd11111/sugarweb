package com.xxd.security;

import com.sugarcoat.protocol.security.TokenInfo;

/**
 * 会话管理
 *
 * @author xxd
 * @since 2023/7/2 14:44
 */
public interface TokenRepository {

    void save(TokenInfo tokenInfo);

    void update(TokenInfo tokenInfo);

    void delete(String accessToken);

    TokenInfo findOne(String accessToken);

    TokenInfo findRefreshToken(String refreshToken);

}
