package com.sugarcoat.support.orm;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * querydsl配置
 *
 * @author xxd
 */
@EntityScan
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class OrmAutoConfiguration {

	@Bean
	public AuditorAware<String> auditorAware(){
		return new SgcAuditorAware();
	}

	@Bean(name = "sgcTenantIdResolver")
	public HibernatePropertiesCustomizer sgcTenantIdResolver(){
		return new SgcTenantIdResolver();
	}

	@Bean(name = "sgcTenantSchemaConnectionProvider")
	public HibernatePropertiesCustomizer sgcTenantSchemaConnectionProvider(DataSource dataSource){
		return new SgcTenantSchemaConnectionProvider(dataSource);
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}



}
