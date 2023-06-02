package com.sugarcoat.dict;

import com.sugarcoat.dict.api.DictionaryClient;
import com.sugarcoat.dict.application.DictionaryService;
import com.sugarcoat.dict.domain.SugarcoatDictionaryClient;
import com.sugarcoat.dict.domain.SugarcoatDictionaryGroupRepository;
import com.sugarcoat.dict.domain.SugarcoatDictionaryRepository;
import com.sugarcoat.dict.application.impl.SugarcoatDictionaryServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author xxd
 * @description 字典工具类初始化
 * @date 2022-11-21
 */
@Configuration(proxyBeanMethods = false)
public class DictionaryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DictionaryService dictService(SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository,
                                         SugarcoatDictionaryRepository sugarcoatDictionaryRepository) {
        return new SugarcoatDictionaryServiceImpl(sugarcoatDictionaryGroupRepository, sugarcoatDictionaryRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryClient dictionaryClient(SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository) {
        return new SugarcoatDictionaryClient(sugarcoatDictionaryGroupRepository);
    }

}