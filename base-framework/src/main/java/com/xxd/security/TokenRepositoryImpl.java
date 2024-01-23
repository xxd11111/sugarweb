package com.xxd.security;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 会话管理实现类
 *
 * @author xxd
 * @since 2023/7/2 20:51
 */
@Component
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final RedissonClient redissonClient;

    private static final String KEY_PREFIX = "session";

    @Override
    public void save(TokenInfo tokenInfo) {
        String key = sessionKey(tokenInfo.getAccessToken());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(tokenInfo);
    }

    @Override
    public void update(TokenInfo tokenInfo) {
        String key = sessionKey(tokenInfo.getAccessToken());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(tokenInfo);
    }

    @Override
    public void delete(String accessToken) {
        String key = sessionKey(accessToken);
        redissonClient.getBucket(key);
    }

    @Override
    public TokenInfo findOne(String accessToken) {
        String key = sessionKey(accessToken);
        RBucket<TokenInfo> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public TokenInfo findRefreshToken(String refreshToken) {
        return null;
    }

    private String sessionKey(String accessToken) {
        return KEY_PREFIX + ":" + accessToken;
    }

}
