package com.sugarcoat.support.orm;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * querydsl配置
 *
 * @author xxd
 */
@EntityScan
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class OrmAutoConfiguration {

	@Resource
	private DynamicDataSourceProperties dynamicDataSourceProperties;

	@Resource
	private HibernateProperties hibernateProperties;

	@Bean
	public AuditorAware<String> auditorAware(){
		return new SgcAuditorAware();
	}

	@Bean(name = "sgcTenantIdResolver")
	public CurrentTenantIdentifierResolver sgcTenantIdResolver(){
		return new SgcTenantIdResolver();
	}

	@Bean(name = "sgcTenantSchemaConnectionProvider")
	public MultiTenantConnectionProvider sgcTenantSchemaConnectionProvider(DataSource dataSource){
		return new SgcTenantSchemaConnectionProvider(dataSource);
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}

	@Bean
	public DataSource dataSource(CurrentTenantIdentifierResolver currentTenantIdentifierResolver){
		return new SgcTenantRoutingDatasource(currentTenantIdentifierResolver, dynamicDataSourceProperties);
	}


}
