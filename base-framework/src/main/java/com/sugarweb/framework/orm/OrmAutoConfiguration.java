package com.sugarweb.framework.orm;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * querydsl配置
 *
 * @author xxd
 */
@Configuration
public class OrmAutoConfiguration implements BeanPostProcessor, WebMvcConfigurer {


}
