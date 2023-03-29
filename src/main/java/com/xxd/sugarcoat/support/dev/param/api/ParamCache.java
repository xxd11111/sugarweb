package com.xxd.sugarcoat.support.dev.param.api;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.Map;

/**
 * @author xxd
 * @description 参数缓存
 * @date 2022-11-18
 */
public interface ParamCache {

    default void saveParam(Collection<Param> params){
        if (CollUtil.isNotEmpty(params)) {
            params.forEach(this::saveParam);
        }
    }

    void saveParam(Param param);

    default void saveParam(String key, String value) {
        saveParam(new Param(key, value));
    }

    void removeParam(String key);

    String getParam(String key);

    Map<String, String> getParam();

    default void reload(Collection<Param> params) {
        clear();
        saveParam(params);
    }

    void clear();
}
