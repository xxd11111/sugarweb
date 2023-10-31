package com.sugarcoat.support.orm;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * querydsl配置
 *
 * @author xxd
 */
@EntityScan
@EnableJpaRepositories
public class OrmAutoConfiguration {

	// @Bean
	// public HibernatePropertiesCustomizer hibernatePropertiesCustomizer1(){
	// 	return new SgcAuditorAware();
	// }

	@Bean
	public HibernatePropertiesCustomizer hibernatePropertiesCustomizer2(){
		return new SgcTenantIdResolver();
	}

	// @Bean
	// public CurrentTenantIdentifierResolver tenantIdResolver(){
	// 	return new SgcTenantIdResolver();
	// }

	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}

}
