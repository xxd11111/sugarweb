package com.xxd.sugarcoat.support.dev.param.utils;

import com.xxd.sugarcoat.support.dev.param.api.ParamService;
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
    public void setParamCache(ParamService paramService) {
        ParamHelper.init(paramService);
    }
}
