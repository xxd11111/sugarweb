package com.sugarcoat.support.dict;

import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.support.dict.application.impl.SugarcoatDictionaryServiceImpl;
import com.sugarcoat.support.dict.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 字典自动配置
 *
 *
 *
 * @author xxd
 * @date 2022-11-21
 */
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(DictionaryProperties.class)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class DictionaryAutoConfiguration {

    private final DictionaryProperties dictionaryProperties;

    @Bean
    @ConditionalOnMissingBean
    public DictionaryService dictService(SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository,
                                         SugarcoatDictionaryRepository sugarcoatDictionaryRepository) {
        return new SugarcoatDictionaryServiceImpl(sugarcoatDictionaryGroupRepository, sugarcoatDictionaryRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryManager dictionaryManager(SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository) {
        return new SugarcoatDictionaryManager(sugarcoatDictionaryGroupRepository);
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public DictionaryScanner dictionaryScanner(DictionaryManager dictionaryManager) {
//        return new DefaultDictionaryScanner(dictionaryManager);
//    }

}
