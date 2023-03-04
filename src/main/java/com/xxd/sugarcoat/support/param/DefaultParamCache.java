package com.xxd.sugarcoat.support.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xxd
 * @description 默认缓存实现方法
 * @date 2022-11-19
 */
@Component
public class DefaultParamCache implements ParamCache {

    private final Map<String, String> paramMap = new ConcurrentHashMap<>();

    @Override
    public void saveParam(Param param) {
        if (param != null && StrUtil.isNotBlank(param.getKey())) {
            paramMap.put(param.getKey(), param.getValue());
        }
    }

    @Override
    public void removeParam(String key) {
        paramMap.remove(key);
    }

    @Override
    public String getParam(String key) {
        return paramMap.get(key);
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> target = new HashMap<>();
        //String时可以使用此方法，若为引用对象时注意深拷贝问题
        BeanUtil.copyProperties(paramMap, target);
        return target;
    }

    @Override
    public void clear() {
        paramMap.clear();
    }
}
