package com.sugarcoat.support.param;

import com.sugarcoat.protocol.param.ParamManager;
import com.sugarcoat.support.param.application.DefaultParamServiceImpl;
import com.sugarcoat.support.param.application.ParamService;
import com.sugarcoat.support.param.domain.*;
import jakarta.annotation.Resource;
import org.redisson.api.RedissonClient;
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
    public ParamCacheManager paramCacheManager(RedissonClient redissonClient) {
        return new DefaultParamCacheManager(paramProperties, redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParamManager paramManager(SgcParamRepository sugarcoatParamRepository, ParamCacheManager paramCacheManager) {
        return new DefaultParamManager(sugarcoatParamRepository, paramCacheManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParamRegistry paramRegistry(SgcParamRepository sugarcoatParamRepository) {
        return new DefaultParamRegistry(sugarcoatParamRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParamRunner paramRunner(ParamScanner paramScanner,
                                   ParamRegistry paramRegistry,
                                   ParamCacheManager paramCacheManager,
                                   SgcParamRepository paramRepository) {
        return new DefaultParamRunner(paramProperties, paramScanner, paramRegistry, paramCacheManager, paramRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParamScanner paramScanner() {
        return new DefaultParamScanner(paramProperties);
    }


}
