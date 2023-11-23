package com.sugarcoat.support.param.domain;

import com.sugarcoat.protocol.parameter.Parameter;
import com.sugarcoat.support.param.ParamProperties;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;

import java.util.Collection;
import java.util.Optional;

/**
 * sugarcoat 默认缓存管理
 *
 * @author xxd
 * @since 2023/9/5 23:17
 */
public class DefaultParamCacheManager implements ParamCacheManager {

    private final String cachePrefix;

    private final RedissonClient redissonClient;

    public DefaultParamCacheManager(ParamProperties paramProperties, RedissonClient redissonClient) {
        this.cachePrefix = paramProperties.getCachePrefix();
        this.redissonClient = redissonClient;
    }

    @Override
    public void put(String code, String value) {
        RBucket<String> bucket = redissonClient.getBucket(getCacheKey(code));
        bucket.set(value);
    }

    @Override
    public void put(Collection<Parameter> parameters) {
        for (Parameter parameter : parameters) {
            RBucket<String> bucket = redissonClient.getBucket(getCacheKey(parameter.getCode()));
            bucket.set(parameter.getValue());
        }
    }

    @Override
    public Optional<String> get(String code) {
        RBucket<String> bucket = redissonClient.getBucket(getCacheKey(code));
        return Optional.of(bucket.get());
    }

    @Override
    public void remove(String code) {
        RBucket<String> bucket = redissonClient.getBucket(getCacheKey(code));
        bucket.delete();
    }

    @Override
    public void clean() {
        RKeys keys = redissonClient.getKeys();
        keys.deleteByPattern(getKeyPattern());
    }

    private String getCacheKey(String str) {
        return cachePrefix + str;
    }

    private String getKeyPattern() {
        return cachePrefix + "*";
    }

}
