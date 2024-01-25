package com.sugarweb.server;

import com.sugarweb.exception.GlobalExceptionHandler;
import com.sugarweb.security.AuthenticateService;
import com.sugarweb.security.AuthenticateFilter;
import com.sugarweb.server.auto.ApiRegister;
import com.sugarweb.server.controller.ApiController;
import com.sugarweb.server.domain.*;
import com.sugarweb.server.application.ApiService;
import com.sugarweb.server.application.impl.ApiServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.WebApplicationContext;

/**
 * 服务自动配置
 *
 * @author xxd
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(ServerProperties.class)
@ConditionalOnProperty(prefix = "sugarweb.server", name = "enable", havingValue = "true")
public class ServerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiService serverApiService(ApiInfoRepository sgcApiInfoRepository) {
        return new ApiServiceImpl(sgcApiInfoRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiController serverApiController(ApiService apiService) {
        return new ApiController(apiService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiLogAspect apiLogAspect(ApiLogInfoHandler apiLogInfoHandler) {
        return new ApiLogAspect(apiLogInfoHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiLogInfoHandler apiLogInfoHandler(ApiCallLogRepository sgcApiCallLogRepository,
                                               ApiErrorLogRepository sgcApiErrorLogRepository,
                                               ApiService apiService) {
        return new ApiLogInfoHandler(sgcApiCallLogRepository, sgcApiErrorLogRepository, apiService);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiRegister apiRegister(WebApplicationContext applicationContext, ApiInfoRepository sgcApiInfoRepository) {
        return new ApiRegister(applicationContext, sgcApiInfoRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerRunner serverRunner(ApiRegister apiRegister){
        return new ServerRunner(apiRegister);
    }

    @Bean
    public AuthenticateFilter authenticateFilter(AuthenticateService authenticateService) {
        return new AuthenticateFilter(authenticateService);
    }

}
