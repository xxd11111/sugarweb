package com.sugarcoat.support.orm;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sugarcoat.support.orm.tenant.SgcTenantIdProcessor;
import com.sugarcoat.support.orm.tenant.SgcTenantIdResolver;
import com.sugarcoat.support.orm.tenant.TenantInterceptor;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * querydsl配置
 *
 * @author xxd
 */
@EntityScan
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class OrmAutoConfiguration implements WebMvcConfigurer {

	@Bean
	public AuditorAware<String> auditorAware(){
		return new SgcAuditorAware();
	}

	@Bean(name = "sgcTenantIdResolver")
	public HibernatePropertiesCustomizer sgcTenantIdResolver(){
		return new SgcTenantIdResolver();
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}

	@Bean
	public DsProcessor sgcDsProcessor(DsProcessor dsProcessor) {
		DsProcessor sgcTenantIdProcessor = new SgcTenantIdProcessor();
		dsProcessor.setNextProcessor(sgcTenantIdProcessor);
		return dsProcessor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TenantInterceptor());
	}

	// DynamicDataSourceContextHolder;
	// DynamicRoutingDataSource;
	// DynamicDataSourceAopConfiguration

}
