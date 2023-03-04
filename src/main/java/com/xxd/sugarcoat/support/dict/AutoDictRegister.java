package com.xxd.sugarcoat.support.dict;

import com.xxd.sugarcoat.support.exception.FrameworkException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author xxd
 * @description 字典工具类初始化
 * @date 2022-11-21
 */
@Component
public class AutoDictRegister {
    private DictCache dictCache;

    @Resource
    public void setDictCache(DictCache dictCache) {
        this.dictCache = dictCache;
    }

    @PostConstruct
    public void init(){
        if (null == dictCache){
            throw new FrameworkException("字典帮助工具初始化异常");
        }
        DictHelper.init(dictCache);
    }

}
