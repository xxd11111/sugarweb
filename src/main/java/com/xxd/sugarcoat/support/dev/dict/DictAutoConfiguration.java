package com.xxd.sugarcoat.support.dev.dict;

import com.xxd.sugarcoat.support.dev.dict.api.DictService;
import com.xxd.sugarcoat.support.dev.dict.application.DefaultDictServiceImpl;
import com.xxd.sugarcoat.support.dev.dict.api.DictItemRepository;
import com.xxd.sugarcoat.support.dev.dict.api.DictGroupRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public DictAutoConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public DictService dictService(DictGroupRepository dictGroupRepository,
                                   DictItemRepository dictItemRepository) {
        return new DefaultDictServiceImpl(dictGroupRepository, dictItemRepository);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
