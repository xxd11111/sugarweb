package com.xxd.sugarcoat.support.param;

import com.xxd.sugarcoat.support.exception.FrameworkException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author xxd
 * @description 参数工具类初始化
 * @date 2022-11-21
 */
@Component
public class ParamInit {
    private ParamCache paramCache;

    @Resource
    public void setParamCache(ParamCache paramCache) {
        this.paramCache = paramCache;
    }

    @PostConstruct
    public void init(){
        if(null == paramCache){
            throw new FrameworkException("参数帮助工具初始化异常");
        }
        ParamHelper.init(paramCache);
    }
}
