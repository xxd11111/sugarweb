package com.sugarweb.server;

import com.sugarweb.framework.exception.GlobalExceptionHandler;
import com.sugarweb.server.application.ApiCallLogService;
import com.sugarweb.server.aspect.ApiLogAspect;
import com.sugarweb.server.controller.ApiCallLogController;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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
@AutoConfiguration
@EnableConfigurationProperties(ServerProperties.class)
@ConditionalOnProperty(prefix = "sugarweb.server", name = "enable", havingValue = "true", matchIfMissing = true)
public class ServerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiLogAspect apiLogAspect() {
        return new ApiLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiCallLogService apiCallLogService() {
        return new ApiCallLogService();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiCallLogController apiCallLogController(ApiCallLogService apiCallLogService) {
        return new ApiCallLogController(apiCallLogService);
    }

}
