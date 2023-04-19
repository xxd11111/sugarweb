package com.xxd.sugarcoat.support.dev.dict.api;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.Map;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-21
 */
public interface DictService {
    default void save(Collection<DictDTO> dictDTOS) {
        if (CollUtil.isNotEmpty(dictDTOS)) {
            dictDTOS.forEach(this::save);
        }
    }

    void save(DictDTO dictItem);

    void remove(String type);

    void remove(String type, String code);

    String get(String type, String code);

    Map<String, String> get(String type);

    default void reload(Collection<DictDTO> dictDTOS) {
        clear();
        save(dictDTOS);
    }

    void clear();
}
