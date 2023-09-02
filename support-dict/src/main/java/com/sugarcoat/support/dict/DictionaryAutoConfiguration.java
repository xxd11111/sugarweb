package com.sugarcoat.support.dict;

import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.support.dict.application.impl.SugarcoatDictionaryServiceImpl;
import com.sugarcoat.support.dict.domain.*;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典自动配置
 *
 * @author xxd
 * @since 2022-11-21
 */
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(DictionaryProperties.class)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class DictionaryAutoConfiguration {

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

    @Bean
    @ConditionalOnMissingBean
    public DictionaryScanner dictionaryScanner(DictionaryProperties properties) {
        return new DefaultDictionaryScanner(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    //todo RedissonClient
    public DictionaryCacheManager dictionaryCacheManager(DictionaryProperties properties, RedissonClient redissonClient) {
        return new DefaultDictionaryCacheManager(properties, redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryInit dictionaryInit(DictionaryProperties properties,
                                         DictionaryScanner dictionaryScanner,
                                         DictionaryManager dictionaryManager,
                                         DictionaryCacheManager dictionaryCacheManager) {

        Map<String, DictionaryRegistry> dictionaryRegistryMap = new HashMap<>();
        dictionaryRegistryMap.put(DictionaryRegistryStrategy.DISABLE.name(), new DisableDictionaryRegistry());
        dictionaryRegistryMap.put(DictionaryRegistryStrategy.INSERT.name(), new InsertDictionaryRegistry());
        dictionaryRegistryMap.put(DictionaryRegistryStrategy.MERGE.name(), new MergeDictionaryRegistry());
        dictionaryRegistryMap.put(DictionaryRegistryStrategy.OVERRIDE.name(), new OverrideDictionaryRegistry());

        return new DefaultDictionaryInit(properties, dictionaryScanner, dictionaryRegistryMap, dictionaryManager, dictionaryCacheManager);
    }


}
