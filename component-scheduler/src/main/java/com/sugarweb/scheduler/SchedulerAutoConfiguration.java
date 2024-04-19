package com.sugarweb.scheduler;

import com.sugarweb.scheduler.auto.SchedulerRunner;
import com.sugarweb.scheduler.controller.SchedulerController;
import com.sugarweb.scheduler.infra.SchedulerManager;
import com.sugarweb.scheduler.infra.QuartzSchedulerManager;
import com.sugarweb.scheduler.domain.SchedulerTaskRepository;
import com.sugarweb.scheduler.application.SchedulerServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务自动注入
 *
 * @author xxd
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
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
