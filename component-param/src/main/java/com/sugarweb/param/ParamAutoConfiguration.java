package com.sugarweb.param;

import com.sugarweb.param.application.ParamService;
import com.sugarweb.param.auto.ParamAutoRegistry;
import com.sugarweb.param.controller.ParamController;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 参数自动配置
 *
 * @author xxd
 * @version 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties(ParamProperties.class)
@ConditionalOnProperty(prefix = "sugarweb.param", name = "enable", havingValue = "true", matchIfMissing = true)
public class ParamAutoConfiguration {

    @Resource
    private ParamProperties paramProperties;

    @Bean
    @ConditionalOnMissingBean
    public ParamService paramService() {
        return new ParamService();
    }

    @Bean
    @ConditionalOnMissingBean
    public ParamAutoRegistry parameterAutoRegistry(ParamService paramService) {
        return new ParamAutoRegistry(paramProperties, paramService);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParamController paramController(ParamService paramService) {
        return new ParamController(paramService);
    }


}
