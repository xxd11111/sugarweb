package com.sugarcoat.support.param;

import com.sugarcoat.support.param.application.DefaultParamServiceImpl;
import com.sugarcoat.support.param.application.ParamService;
import com.sugarcoat.support.param.domain.SugarcoatParamRepository;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public ParamService getParamService(SugarcoatParamRepository sugarcoatParamRepository) {
        return new DefaultParamServiceImpl(sugarcoatParamRepository);
    }

}
