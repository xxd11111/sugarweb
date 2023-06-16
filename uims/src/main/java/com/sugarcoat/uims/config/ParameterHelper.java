package com.sugarcoat.uims.config;

import cn.hutool.extra.spring.SpringUtil;
import com.sugarcoat.api.param.ParameterManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xxd
 * @description 参数使用帮助类
 * @date 2022-11-19
 */

@Slf4j
public class ParameterHelper {

    public static String getValue(String code) {
        return getInstance().getValue(code);
    }

    private static ParameterManager getInstance() {
        return ParameterHelperInner.PARAMETER_CLIENT;
    }

    private static class ParameterHelperInner {
        private static final ParameterManager PARAMETER_CLIENT = SpringUtil.getBean(ParameterManager.class);
    }
}
