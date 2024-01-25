package com.sugarweb.support.scheduler;

import com.sugarweb.support.scheduler.api.SchedulerManager;
import com.sugarweb.support.scheduler.controller.SchedulerController;
import com.sugarweb.support.scheduler.domain.SchedulerRunner;
import com.sugarweb.support.scheduler.domain.QuartzSchedulerManager;
import com.sugarweb.support.scheduler.domain.SchedulerTaskRepository;
import com.sugarweb.support.scheduler.application.SchedulerServiceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 定时任务自动注入
 *
 * @author xxd
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
@EntityScan
@EnableJpaRepositories
@EnableConfigurationProperties(SchedulerProperties.class)
// @ConditionalOnProperty(prefix = "sugarweb.scheduler", name = "enable", havingValue = "true")
public class SchedulerAutoConfiguration {

    @Bean
    public SchedulerManager schedulerManager() {
        return new QuartzSchedulerManager();
    }

    @Bean
    public SchedulerServiceImpl schedulerService(SchedulerManager schedulerManager, SchedulerTaskRepository sgcSchedulerTaskRepository) {
        return new SchedulerServiceImpl(schedulerManager, sgcSchedulerTaskRepository);
    }

    @Bean
    public SchedulerController schedulerController() {
        return new SchedulerController();
    }

    @Bean
    public SchedulerRunner schedulerRunner(SchedulerManager schedulerManager) {
        return new SchedulerRunner(schedulerManager);
    }

}
