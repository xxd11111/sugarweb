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
public class AccessTokenRepositoryImpl implements AccessTokenRepository {

    private final RedissonClient redissonClient;

    private static final String KEY_PREFIX = "accessToken:";

    @Override
    public void save(AccessTokenInfo accessTokenInfo) {
        String key = sessionKey(accessTokenInfo.getAccessToken());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(accessTokenInfo);
    }

    @Override
    public void update(AccessTokenInfo accessTokenInfo) {
        String key = sessionKey(accessTokenInfo.getAccessToken());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(accessTokenInfo);
    }

    @Override
    public void delete(String accessToken) {
        String key = sessionKey(accessToken);
        redissonClient.getBucket(key);
    }

    @Override
    public AccessTokenInfo findOne(String accessToken) {
        String key = sessionKey(accessToken);
        RBucket<AccessTokenInfo> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    private String sessionKey(String accessToken) {
        return KEY_PREFIX + accessToken;
    }

}
