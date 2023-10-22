package com.sugarcoat.orm;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;
import org.springframework.data.domain.AuditorAware;

/**
 * querydsl配置
 *
 * @author xxd
 */
@Configuration
public class OrmAutoConfiguration {

	@Bean
	public SgcAuditorAware auditorAware(){
		return new SgcAuditorAware();
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}

}
