package com.sugarcoat.support.dict.domain;

import cn.hutool.core.collection.CollUtil;
import com.sugarcoat.api.dict.Dictionary;
import com.sugarcoat.api.dict.DictionaryGroup;
import com.sugarcoat.support.dict.DictionaryProperties;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.*;

/**
 * 字典缓存管理
 *
 * @author xxd
 * @since 2023/9/2 22:45
 */
public class DefaultDictionaryCacheManager implements DictionaryCacheManager{

    private final String cachePrefix;

    private final RedissonClient redissonClient;

    public DefaultDictionaryCacheManager(DictionaryProperties dictionaryProperties, RedissonClient redissonClient) {
        this.cachePrefix = dictionaryProperties.getCachePrefix();
        this.redissonClient = redissonClient;
    }

    public void put(String groupCode, String dictionaryCode, String dictionaryName) {
        RMap<String, String> map = redissonClient.getMap(groupCode);
        map.put(dictionaryCode, dictionaryName);
    }

    public void put(List<DictionaryGroup> dictionaryGroups) {
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

    public Optional<String> get(String groupCode, String dictionaryCode) {
        String cacheKey = getCacheKey(groupCode);
        RMap<String, String> map = redissonClient.getMap(cacheKey);
        String dictionaryName = map.get(dictionaryCode);
        return Optional.of(dictionaryName);
    }

    private String getCacheKey(String str) {
        return cachePrefix + str;
    }


}
