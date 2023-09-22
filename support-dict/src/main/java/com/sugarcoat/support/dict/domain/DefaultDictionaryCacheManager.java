package com.sugarcoat.support.dict.domain;

import cn.hutool.core.collection.CollUtil;
import com.sugarcoat.protocol.dict.Dictionary;
import com.sugarcoat.protocol.dict.DictionaryGroup;
import com.sugarcoat.support.dict.DictionaryProperties;
import org.redisson.api.RKeys;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 字典缓存管理
 *
 * @author xxd
 * @since 2023/9/2 22:45
 */
public class DefaultDictionaryCacheManager implements DictionaryCacheManager {

    private final String cachePrefix;

    private final RedissonClient redissonClient;

    public DefaultDictionaryCacheManager(DictionaryProperties dictionaryProperties, RedissonClient redissonClient) {
        this.cachePrefix = dictionaryProperties.getCachePrefix();
        this.redissonClient = redissonClient;
    }

    @Override
    public void put(String groupCode, String dictionaryCode, String dictionaryName) {
        RMap<String, String> map = redissonClient.getMap(groupCode);
        map.put(dictionaryCode, dictionaryName);
    }

    @Override
    public void put(Collection<DictionaryGroup> dictionaryGroups) {
        if (CollUtil.isEmpty(dictionaryGroups)) {
            return;
        }
        for (DictionaryGroup dictionaryGroup : dictionaryGroups) {
            Collection<Dictionary> dictionaries = dictionaryGroup.getDictionaries();
            Map<String, String> cacheMap = new HashMap<>();
            for (Dictionary dictionary : dictionaries) {
                cacheMap.put(dictionary.getCode(), dictionary.getName());
            }
            RMap<String, String> map = redissonClient.getMap(dictionaryGroup.getGroupCode());
            map.putAll(cacheMap);
        }
    }

    @Override
    public Optional<String> get(String groupCode, String dictionaryCode) {
        String cacheKey = getCacheKey(groupCode);
        RMap<String, String> map = redissonClient.getMap(cacheKey);
        String dictionaryName = map.get(dictionaryCode);
        return Optional.of(dictionaryName);
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
