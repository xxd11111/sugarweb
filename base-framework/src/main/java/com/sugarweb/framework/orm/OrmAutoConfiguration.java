package com.sugarweb.framework.orm;

import com.sugarweb.framework.orm.datapermission.*;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * querydsl配置
 *
 * @author xxd
 */
@Configuration
public class OrmAutoConfiguration implements BeanPostProcessor, WebMvcConfigurer {

    @Bean
    public Advisor dataPermissionFilterAdvisor() {
        DataPermissionMethodInterceptor interceptor = new DataPermissionMethodInterceptor();
        return new DataPermissionFilterAdvisor(interceptor, DataPermissionIgnore.class);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataPermissionInterceptor());
    }

}
