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
public class RedisAccessTokenRepository implements AccessTokenRepository {

    private final RedissonClient redissonClient;

    private static final String KEY_PREFIX = "accessToken:";

    @Override
    public void save(AccessToken accessToken) {
        String key = sessionKey(accessToken.getAccessToken());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(accessToken);
    }

    @Override
    public void update(AccessToken accessToken) {
        String key = sessionKey(accessToken.getAccessToken());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(accessToken);
    }

    @Override
    public void delete(String accessToken) {
        String key = sessionKey(accessToken);
        redissonClient.getBucket(key);
    }

    @Override
    public AccessToken findOne(String accessToken) {
        String key = sessionKey(accessToken);
        RBucket<AccessToken> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    private String sessionKey(String accessToken) {
        return KEY_PREFIX + accessToken;
    }

}
