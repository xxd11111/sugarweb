package com.xxd.sugarcoat.support.dict;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xxd
 * @description 字典工具类初始化
 * @date 2022-11-21
 */
@Component
public class AutoDictRegister {

    @Resource
    public void init(DictCache dictCache){
        DictHelper.init(dictCache);
    }

}
