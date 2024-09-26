package com.sugarweb.dict;

import com.sugarweb.dict.application.DictService;
import com.sugarweb.dict.controller.DictController;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 字典自动配置
 *
 * @author xxd
 * @version 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties(DictProperties.class)
@ConditionalOnProperty(prefix = "sugarweb.dictionary", name = "enable", havingValue = "true", matchIfMissing = true)
public class DictAutoConfiguration {

    @Resource
    private DictProperties dictProperties;

    @Bean
    @ConditionalOnMissingBean
    public DictService dictService() {
        return new DictService();
    }

    @Bean
    @ConditionalOnMissingBean
    public DictController dictionaryController(DictService dictService) {
        return new DictController(dictService);
    }

}
