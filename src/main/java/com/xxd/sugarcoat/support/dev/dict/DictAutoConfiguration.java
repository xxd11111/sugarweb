package com.xxd.sugarcoat.support.dev.dict;

import com.xxd.sugarcoat.support.dev.dict.api.DefaultDictServiceImpl;
import com.xxd.sugarcoat.support.dev.dict.api.DictService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author xxd
 * @description 字典工具类初始化
 * @date 2022-11-21
 */
@Configuration(proxyBeanMethods = false)
public class DictAutoConfiguration implements InitializingBean {

    private final ApplicationContext applicationContext;

    public DictAutoConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        DictHelper.dictService = applicationContext.getBean(DictService.class);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictService dictService() {
        return new DefaultDictServiceImpl();
    }



}
