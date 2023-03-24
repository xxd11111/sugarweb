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
    default void save(Collection<BaseDict> baseDicts){
        if (CollUtil.isNotEmpty(baseDicts)) {
            baseDicts.forEach(this::save);
        }
    }

    void save(BaseDict baseDict);

    void remove(String type);

    void remove(String type, String code);

    String get(String type, String code);

    Map<String, String> get(String type);

    default void reload(Collection<BaseDict> baseDicts){
        clear();
        save(baseDicts);
    }

    void clear();
}
