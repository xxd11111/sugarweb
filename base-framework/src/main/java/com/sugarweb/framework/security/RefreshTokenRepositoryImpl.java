package com.sugarweb.framework.security;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 会话管理实现类
 *
 * @author xxd
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RedissonClient redissonClient;

    private static final String KEY_PREFIX = "session";

    @Override
    public void save(RefreshTokenInfo refreshTokenInfo) {
        String key = tokenKey(refreshTokenInfo.getRefreshToken());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(refreshTokenInfo);
    }

    @Override
    public void update(RefreshTokenInfo refreshTokenInfo) {
        String key = tokenKey(refreshTokenInfo.getRefreshToken());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(refreshTokenInfo);
    }

    @Override
    public void delete(String refreshToken) {
        String key = tokenKey(refreshToken);
        redissonClient.getBucket(key);
    }

    @Override
    public RefreshTokenInfo findOne(String refreshToken) {
        String key = tokenKey(refreshToken);
        RBucket<RefreshTokenInfo> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    private String tokenKey(String refreshToken) {
        return KEY_PREFIX + ":" + refreshToken;
    }

}
