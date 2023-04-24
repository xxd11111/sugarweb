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

    void save(DictDTO dictDTO);

    void update(DictDTO dictDTO);

    void remove(String id);

    void remove(String type, String code);

}
