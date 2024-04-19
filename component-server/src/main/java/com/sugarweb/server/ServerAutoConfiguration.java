package com.sugarweb.server;

import com.sugarweb.framework.exception.GlobalExceptionHandler;
import com.sugarweb.framework.security.AuthenticateService;
import com.sugarweb.framework.security.AuthenticateFilter;
import com.sugarweb.server.aspect.ApiLogAspect;
import com.sugarweb.server.domain.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务自动配置
 *
 * @author xxd
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ServerProperties.class)
@ConditionalOnProperty(prefix = "sugarweb.server", name = "enable", havingValue = "true")
public class ServerAutoConfiguration {

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
                                               ApiErrorLogRepository sgcApiErrorLogRepository) {
        return new ApiLogInfoHandler(sgcApiCallLogRepository, sgcApiErrorLogRepository);
    }

    @Bean
    public AuthenticateFilter authenticateFilter(AuthenticateService authenticateService) {
        return new AuthenticateFilter(authenticateService);
    }

}
