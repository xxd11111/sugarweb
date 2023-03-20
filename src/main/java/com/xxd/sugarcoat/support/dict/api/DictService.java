package com.xxd.sugarcoat.support.dict.api;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.Map;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public interface DictService {
    default void saveDict(Collection<BaseDict> baseDicts){
        if (CollUtil.isNotEmpty(baseDicts)) {
            baseDicts.forEach(this::saveDict);
        }
    }

    void saveDict(BaseDict baseDict);

    void removeDict(String type);

    void removeDict(String type, String code);

    String getDict(String type, String code);

    Map<String, String> getDict(String type);

    default void reload(Collection<BaseDict> baseDicts){
        saveDict(baseDicts);
        clear();
    }

    void clear();
}
