package com.sugarweb.support.param;

import com.sugarweb.support.param.application.ParamServiceImpl;
import com.sugarweb.support.param.application.ParamService;
import com.sugarweb.support.param.auto.ParamAutoRegistry;
import com.sugarweb.support.param.domain.*;
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
    public ParamService paramService(ParamRepository sugarcoatParamRepository) {
        return new ParamServiceImpl(sugarcoatParamRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParamAutoRegistry parameterAutoRegistry(ParamRepository sgcParamRepository) {
        return new ParamAutoRegistry(paramProperties, sgcParamRepository);
    }


}
