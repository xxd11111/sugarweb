package com.sugarcoat.support.parameter;

import com.sugarcoat.support.parameter.api.ParameterManager;
import com.sugarcoat.support.parameter.application.DefaultParamServiceImpl;
import com.sugarcoat.support.parameter.application.ParamService;
import com.sugarcoat.support.parameter.auto.ParameterAutoRegistry;
import com.sugarcoat.support.parameter.domain.*;
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
 * @since 2023/5/7 0:00
 */
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(ParamProperties.class)
public class ParameterAutoConfiguration {

    @Resource
    private ParamProperties paramProperties;

    @Bean
    @ConditionalOnMissingBean
    public ParamService paramService(SgcParamRepository sugarcoatParamRepository) {
        return new DefaultParamServiceImpl(sugarcoatParamRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParameterManager paramManager(SgcParamRepository sugarcoatParamRepository) {
        return new DefaultParameterManager(sugarcoatParamRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParameterAutoRegistry parameterAutoRegistry(SgcParamRepository sgcParamRepository) {
        return new ParameterAutoRegistry(paramProperties, sgcParamRepository);
    }


}
