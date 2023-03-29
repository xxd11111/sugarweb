package com.xxd.sugarcoat.support.dev.param.utils;

import com.xxd.sugarcoat.support.dev.param.api.ParamCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xxd
 * @description 参数工具类初始化
 * @date 2022-11-21
 */
@Component
public class ParamInit {

    @Resource
    public void setParamCache(ParamCache paramCache) {
        ParamUtil.init(paramCache);
    }
}
