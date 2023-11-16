package com.sugarcoat.support.orm;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDatasourceAopProperties;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sugarcoat.protocol.BeanUtil;
import com.sugarcoat.support.orm.datapermission.*;
import com.sugarcoat.support.orm.softdelete.SoftDeleteBinder;
import com.sugarcoat.support.orm.tenant.SgcTenantIdProcessor;
import com.sugarcoat.support.orm.tenant.SgcTenantIdResolver;
import com.sugarcoat.support.orm.tenant.TenantInterceptor;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.internal.SessionImpl;
import org.hibernate.tuple.TenantIdBinder;
import org.springframework.aop.Advisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;
import java.util.function.Consumer;

/**
 * querydsl配置
 *
 * @author xxd
 */
@EntityScan
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class OrmAutoConfiguration implements WebMvcConfigurer, BeanPostProcessor {

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

	@Bean
	public Advisor dataPermissionFilterAdvisor() {
		// LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;
		// localContainerEntityManagerFactoryBean.setEntityManagerInitializer();
		DataPermissionFilterAspect interceptor = new DataPermissionFilterAspect();
		return new DataPermissionFilterAdvisor(interceptor, DataPermissionFilter.class);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof LocalContainerEntityManagerFactoryBean entityManagerFactory){
			entityManagerFactory.setEntityManagerInitializer(entityManager -> {
				if (entityManager instanceof SessionImpl session) {
					LoadQueryInfluencers loadQueryInfluencers = session.getLoadQueryInfluencers();
					loadQueryInfluencers.enableFilter(DataPermissionBinder.FILTER_NAME)
							.setParameter(DataPermissionBinder.PARAMETER_NAME, "1");
				}
			});
		}
		return bean;
	}

	// DynamicDataSourceContextHolder;
	// DynamicRoutingDataSource;
	// DynamicDataSourceAopConfiguration

}
