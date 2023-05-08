package com.xxd.sugarcoat.support.dict;

import com.xxd.sugarcoat.support.dict.api.DictService;
import com.xxd.sugarcoat.support.dict.application.DefaultDictServiceImpl;
import com.xxd.sugarcoat.support.dict.domain.DictGroupRepository;
import com.xxd.sugarcoat.support.dict.domain.DictItemRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author xxd
 * @description 字典工具类初始化
 * @date 2022-11-21
 */
@Configuration(proxyBeanMethods = false)
public class DictAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DictService dictService(DictGroupRepository dictGroupRepository,
                                   DictItemRepository dictItemRepository) {
        return new DefaultDictServiceImpl(dictGroupRepository, dictItemRepository);
    }

}
