package com.xxd.sugarcoat.support.dev.param.utils;

import com.xxd.sugarcoat.support.dev.exception.FrameworkException;
import com.xxd.sugarcoat.support.dev.param.api.Param;
import com.xxd.sugarcoat.support.dev.param.api.ParamCache;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

/**
 * @author xxd
 * @description 参数使用帮助类
 * @date 2022-11-19
 */

@Slf4j
public class ParamUtil {

    private static ParamCache paramCache;

    protected static void init(ParamCache paramCache) {
        if (null != ParamUtil.paramCache) {
            throw new FrameworkException("=====> paramHelper初始化异常:该对象只能初始化一次");
        }
        ParamUtil.paramCache = paramCache;
        log.info("=====> paramHelper初始化成功");
    }

    public static void saveParam(Collection<Param> params) {
        paramCache.saveParam(params);
    }

    public static void saveParam(Param param) {
        paramCache.saveParam(param);
    }

    public static void saveParam(String key, String value) {
        paramCache.saveParam(key, value);
    }

    public static void removeParam(String key) {
        paramCache.removeParam(key);
    }

    public static String getParam(String key) {
        return paramCache.getParam(key);
    }

    public static Map<String, String> getParam() {
        return paramCache.getParam();
    }

    public static void reload(Collection<Param> params) {
        paramCache.reload(params);
    }

    public static void clear() {
        paramCache.clear();
    }
}
