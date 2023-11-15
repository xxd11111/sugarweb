package com.sugarcoat.support.dict;

import com.sugarcoat.protocol.dict.DictionaryManager;
import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.support.dict.application.impl.SugarcoatDictionaryServiceImpl;
import com.sugarcoat.support.dict.domain.*;
import jakarta.annotation.Resource;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
@ConditionalOnProperty(prefix = "sugarcoat.dictionary", name = "enable", havingValue = "true")
public class DictionaryAutoConfiguration {

    @Resource
    private DictionaryProperties dictionaryProperties;

    @Bean
    @ConditionalOnMissingBean
    public DictionaryService dictService(SgcDictionaryGroupRepository sugarcoatDictionaryGroupRepository,
                                         SgcDictionaryRepository sugarcoatDictionaryRepository) {
        return new SugarcoatDictionaryServiceImpl(sugarcoatDictionaryGroupRepository, sugarcoatDictionaryRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryManager dictionaryManager(SgcDictionaryGroupRepository sugarcoatDictionaryGroupRepository) {
        return new SugarcoatDictionaryManager(sugarcoatDictionaryGroupRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryScanner dictionaryScanner() {
        return new DefaultDictionaryScanner(dictionaryProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryCacheManager dictionaryCacheManager(RedissonClient redissonClient) {
        return new DefaultDictionaryCacheManager(dictionaryProperties, redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryRunner dictionaryRunner(DictionaryScanner dictionaryScanner,
                                           DictionaryManager dictionaryManager,
                                           DictionaryCacheManager dictionaryCacheManager) {

        Map<String, DictionaryRegistry> dictionaryRegistryMap = new HashMap<>();
        dictionaryRegistryMap.put(DictionaryRegistryStrategy.DISABLE.name(), new DisableDictionaryRegistry());
        dictionaryRegistryMap.put(DictionaryRegistryStrategy.INSERT.name(), new InsertDictionaryRegistry(dictionaryManager));
        dictionaryRegistryMap.put(DictionaryRegistryStrategy.MERGE.name(), new MergeDictionaryRegistry(dictionaryManager));
        dictionaryRegistryMap.put(DictionaryRegistryStrategy.OVERRIDE.name(), new OverrideDictionaryRegistry(dictionaryManager));

        return new DefaultDictionaryRunner(dictionaryProperties, dictionaryScanner, dictionaryRegistryMap, dictionaryManager, dictionaryCacheManager);
    }


}
