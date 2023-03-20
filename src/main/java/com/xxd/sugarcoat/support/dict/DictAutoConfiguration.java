package com.xxd.sugarcoat.support.dict;

import com.xxd.sugarcoat.support.dict.api.DictService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xxd
 * @description 字典工具类初始化
 * @date 2022-11-21
 */
@Component
public class DictAutoConfiguration {

    @Resource
    public void init(DictService dictService){
        DictHelper.init(dictService);
    }

}
