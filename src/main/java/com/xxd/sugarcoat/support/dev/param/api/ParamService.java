package com.xxd.sugarcoat.support.dev.param.api;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xxd
 * @description 参数缓存
 * @date 2022-11-18
 */
public interface ParamService {

    void saveParam(ParamDTO paramDTO);

    void removeParam(String key);

    ParamVO getParam(String key);

    Map<String, String> getParamMap();

    List<Param> getAll();

    //default void reload(Collection<ParamDTO> params) {
    //    clear();
    //    saveParam(getAll());
    //}
    //
    //void clear();
}
