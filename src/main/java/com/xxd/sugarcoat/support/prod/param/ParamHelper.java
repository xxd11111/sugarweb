package com.xxd.sugarcoat.support.prod.param;

import cn.hutool.extra.spring.SpringUtil;
import com.xxd.sugarcoat.support.prod.param.api.ParamService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xxd
 * @description 参数使用帮助类
 * @date 2022-11-19
 */

@Slf4j
public class ParamHelper {

    //todo innerclass
    private static final ParamService paramService = SpringUtil.getBean(ParamService.class);

    public static String getValue(String code) {
        return paramService.findByCode(code).getValue();
    }

}
