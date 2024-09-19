package com.sugarweb.dict;

import com.sugarweb.dict.application.DictService;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 字典自动配置
 *
 * @author xxd
 * @version 1.0
 */
@EntityScan
@EnableConfigurationProperties(DictionaryProperties.class)
@ConditionalOnProperty(prefix = "sugarweb.dictionary", name = "enable", havingValue = "true")
public class DictionaryAutoConfiguration {

    @Resource
    private DictionaryProperties dictionaryProperties;

    @Bean
    @ConditionalOnMissingBean
    public DictService dictService() {
        return new DictService();
    }

}
