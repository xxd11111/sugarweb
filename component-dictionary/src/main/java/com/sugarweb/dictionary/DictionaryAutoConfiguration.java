package com.sugarweb.dictionary;

import com.sugarweb.dictionary.application.DictionaryService;
import com.sugarweb.dictionary.application.impl.DictionaryServiceImpl;
import com.sugarweb.dictionary.auto.DictionaryAutoRegistry;
import com.sugarweb.dictionary.domain.DictionaryRepository;
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
 * @version 1.0
 */
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(DictionaryProperties.class)
@ConditionalOnProperty(prefix = "sugarweb.dictionary", name = "enable", havingValue = "true")
public class DictionaryAutoConfiguration {

    @Resource
    private DictionaryProperties dictionaryProperties;

    @Bean
    @ConditionalOnMissingBean
    public DictionaryService dictService(DictionaryRepository dictionaryRepository) {
        return new DictionaryServiceImpl(dictionaryRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public DictionaryAutoRegistry dictionaryAutoRegistry(DictionaryService dictionaryService) {
        return new DictionaryAutoRegistry(dictionaryService);
    }

}
