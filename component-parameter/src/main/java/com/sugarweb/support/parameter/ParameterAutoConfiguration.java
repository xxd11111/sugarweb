package com.sugarweb.support.parameter;

import com.sugarweb.support.parameter.api.ParameterManager;
import com.sugarweb.support.parameter.application.DefaultParamServiceImpl;
import com.sugarweb.support.parameter.application.ParamService;
import com.sugarweb.support.parameter.auto.ParameterAutoRegistry;
import com.sugarweb.support.parameter.domain.*;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 参数自动配置
 *
 * @author xxd
 * @version 1.0
 */
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(ParamProperties.class)
public class ParameterAutoConfiguration {

    @Resource
    private ParamProperties paramProperties;

    @Bean
    @ConditionalOnMissingBean
    public ParamService paramService(BaseParamRepository sugarcoatParamRepository) {
        return new DefaultParamServiceImpl(sugarcoatParamRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParameterManager paramManager(BaseParamRepository sugarcoatParamRepository) {
        return new DefaultParameterManager(sugarcoatParamRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParameterAutoRegistry parameterAutoRegistry(BaseParamRepository sgcParamRepository) {
        return new ParameterAutoRegistry(paramProperties, sgcParamRepository);
    }


}
