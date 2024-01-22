package com.xxd.server;

import com.sugarcoat.protocol.security.AuthenticateService;
import com.sugarcoat.protocol.server.ApiManager;
import com.xxd.DataPermissionInterceptor;
import com.xxd.TenantInterceptor;
import com.xxd.security.AuthenticateFilter;
import com.xxd.server.auto.ApiRegister;
import com.xxd.server.controller.ApiController;
import com.xxd.server.service.SgcApiService;
import com.xxd.server.service.impl.SgcApiServiceImpl;
import com.xxd.server.domain.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 服务自动配置
 *
 * @author xxd
 * @since 2023/5/8 23:28
 */
@Configuration(proxyBeanMethods = false)
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(ServerProperties.class)
@ConditionalOnProperty(prefix = "sugarcoat.server", name = "enable", havingValue = "true")
public class ServerAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnMissingBean
    public SgcApiService serverApiService(SgcApiRepository sgcApiRepository) {
        return new SgcApiServiceImpl(sgcApiRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiController serverApiController(SgcApiService sgcApiService) {
        return new ApiController(sgcApiService);
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
    public ApiLogInfoHandler apiLogInfoHandler(SgcApiCallLogRepository sgcApiCallLogRepository,
                                               SgcApiErrorLogRepository sgcApiErrorLogRepository,
                                               ApiManager apiManager) {
        return new ApiLogInfoHandler(sgcApiCallLogRepository, sgcApiErrorLogRepository, apiManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiManager apiManager(SgcApiRepository sgcApiRepository, ApiRegister apiRegister) {
        return new SgcApiManager(sgcApiRepository, apiRegister);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiRegister apiRegister(WebApplicationContext applicationContext, SgcApiRepository sgcApiRepository) {
        return new ApiRegister(applicationContext, sgcApiRepository);
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantInterceptor());
        registry.addInterceptor(new DataPermissionInterceptor());
    }
}
