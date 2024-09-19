package com.sugarweb.framework;

import com.sugarweb.framework.security.SaTokenConfigure;
import com.sugarweb.framework.utils.BeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * FrameworkAutoConfiguration
 *
 * @author 许向东
 * @version 1.0
 */
// @EnableConfigurationProperties()
@Configuration
public class FrameworkAutoConfiguration {

    @Bean
    @Order(-1)
    public BeanUtil beanUtil() {
        return new BeanUtil();
    }

    @Bean
    SaTokenConfigure saTokenConfigure() {
        return new SaTokenConfigure();
    }
}
