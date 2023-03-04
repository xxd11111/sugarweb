package com.xxd.sugarcoat.support.dict;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.Map;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public interface DictCache {
    default void saveDict(Collection<Dict> dicts){
        if (CollUtil.isNotEmpty(dicts)) {
            dicts.forEach(this::saveDict);
        }
    }

    void saveDict(Dict dict);

    void removeDict(String type);

    void removeDict(String type, String code);

    String getDict(String type, String code);

    Map<String, String> getDict(String type);

    default void reload(Collection<Dict> dicts){
        saveDict(dicts);
        clear();
    }

    void clear();
}
