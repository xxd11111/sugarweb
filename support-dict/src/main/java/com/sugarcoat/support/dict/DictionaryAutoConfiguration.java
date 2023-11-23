package com.sugarcoat.support.dict;

import com.sugarcoat.protocol.dictionary.DictionaryManager;
import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.support.dict.application.impl.SugarcoatDictionaryServiceImpl;
import com.sugarcoat.support.dict.domain.*;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 字典自动配置
 *
 * @author xxd
 * @since 2022-11-21
 */
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(DictionaryProperties.class)
@ConditionalOnProperty(prefix = "sugarcoat.dictionary", name = "enable", havingValue = "true")
public class DictionaryAutoConfiguration {

    @Resource
    private DictionaryProperties dictionaryProperties;

    @Bean
    @ConditionalOnMissingBean
    public DictionaryService dictService(DictionaryManager<SugarcoatDictionary> dictionaryManager, SgcDictionaryRepository sgcDictionaryRepository) {
        return new SugarcoatDictionaryServiceImpl(dictionaryManager, sgcDictionaryRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryManager<SugarcoatDictionary> dictionaryManager(SgcDictionaryRepository sgcDictionaryRepository) {
        return new SugarcoatDictionaryManager(sgcDictionaryRepository);
    }

}
