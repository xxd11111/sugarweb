package com.sugarcoat.support.orm;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sugarcoat.support.orm.datapermission.*;
import com.sugarcoat.support.orm.tenant.SgcTenantIdProcessor;
import com.sugarcoat.support.orm.tenant.SgcTenantIdResolver;
import com.sugarcoat.support.orm.tenant.TenantInterceptor;
import jakarta.persistence.EntityManager;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionImpl;
import org.springframework.aop.Advisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public AuditorAware<String> auditorAware() {
        return new SgcAuditorAware();
    }

    @Bean(name = "sgcTenantIdResolver")
    public HibernatePropertiesCustomizer sgcTenantIdResolver() {
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
        registry.addInterceptor(new DataPermissionInterceptor());
    }

    @Bean
    public Advisor dataPermissionFilterAdvisor() {
        DataPermissionMethodInterceptor interceptor = new DataPermissionMethodInterceptor();
        return new DataPermissionFilterAdvisor(interceptor, DataPermissionIgnore.class);
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LocalContainerEntityManagerFactoryBean entityManagerFactory) {
            Consumer<EntityManager> entityManagerConsumer = entityManager -> {
                SessionImpl session = (SessionImpl) entityManager;
                SessionFactoryImplementor factory = session.getFactory();
                //  数据权限
                if (factory.getDefinedFilterNames().contains(DataPermissionBinder.FILTER_NAME)) {
                    DataPermissionInfo dataPermissionInfo = DataPermissionContext.getDataPermissionInfo();
                    if (dataPermissionInfo == null){
                        return;
                    }
                    String strategy = dataPermissionInfo.getStrategy();
                    if (DataPermissionContext.isIgnore() ||
                            dataPermissionInfo.isRoot() ||
                            StrUtil.equals(DataPermissionStrategy.all.getValue(), strategy)) {
                        return;
                    }
                    Object value = dataPermissionInfo.getValue(strategy);
                    LoadQueryInfluencers loadQueryInfluencers = session.getLoadQueryInfluencers();
                    loadQueryInfluencers.enableFilter(DataPermissionBinder.FILTER_NAME)
                            .setParameter(DataPermissionBinder.PARAMETER_NAME, value);
                }
            };
            entityManagerFactory.setEntityManagerInitializer(entityManagerConsumer);
        }
        return bean;
    }


}
