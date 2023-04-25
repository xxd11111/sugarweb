package com.xxd.sugarcoat.support.dev.param.utils;

import com.xxd.sugarcoat.support.dev.exception.FrameworkException;
import com.xxd.sugarcoat.support.dev.param.api.Param;
import com.xxd.sugarcoat.support.dev.param.api.ParamService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

/**
 * @author xxd
 * @description 参数使用帮助类
 * @date 2022-11-19
 */

@Slf4j
public class ParamHelper {

    private static ParamService paramService;

    protected static void init(ParamService paramService) {
        if (null != ParamHelper.paramService) {
            throw new FrameworkException("=====> paramHelper初始化异常:该对象只能初始化一次");
        }
        ParamHelper.paramService = paramService;
        log.info("=====> paramHelper初始化成功");
    }

    public static String getParam(String key) {
        return paramService.getParam(key).getValue();
    }

    //public static Map<String, String> getParam() {
    //    return paramService.getParam();
    //}

    //public static void reload() {
    //    paramService.reload(params);
    //}

}
