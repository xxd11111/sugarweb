package com.xxd.orm.audit;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * querydsl配置
 *
 * @author xxd
 */
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class OrmAutoConfiguration implements BeanPostProcessor {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new SgcAuditorAware();
    }

}
