package com.sugarweb.framework;

import com.sugarweb.framework.common.BeanUtil;
import org.springframework.context.annotation.Bean;

/**
 * FrameworkAutoConfiguration
 *
 * @author 许向东
 * @version 1.0
 */
// @EnableConfigurationProperties()
public class FrameworkAutoConfiguration {

    @Bean
    public BeanUtil beanUtil(){
        return new BeanUtil();
    }
}
