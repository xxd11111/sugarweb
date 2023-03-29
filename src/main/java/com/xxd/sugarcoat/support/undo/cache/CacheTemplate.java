package com.xxd.sugarcoat.support.undo.cache;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public interface CacheTemplate<K, V> {
    K get(K key);

    boolean put(K key, V value);

    boolean put(K key, V value, long timeout);

    //TODO 反向转为消费模式
    Collection<K> keys();

    //TODO 反向转为消费模式
    Collection<K> keys(Pattern pattern);
}
