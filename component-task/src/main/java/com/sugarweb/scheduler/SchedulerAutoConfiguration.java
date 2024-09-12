package com.sugarweb.scheduler;

import com.sugarweb.scheduler.application.TaskService;
import com.sugarweb.scheduler.controller.SchedulerController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;

/**
 * 定时任务自动注入
 *
 * @author xxd
 * @version 1.0
 */
@EnableScheduling
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SchedulerProperties.class)
@ConditionalOnProperty(prefix = "sugarweb.scheduler", name = "enable", havingValue = "true")
public class SchedulerAutoConfiguration {

    @Bean
    public SimpleAsyncTaskScheduler taskScheduler() {
        SimpleAsyncTaskScheduler scheduler = new SimpleAsyncTaskScheduler();
        scheduler.setVirtualThreads(true);
        return scheduler;
    }

    @Bean
    public SchedulingConfigurer schedulingConfigurer(SimpleAsyncTaskScheduler scheduler) {
        return new SpringTaskConfiguration(scheduler);
    }

    @Bean
    public TaskService taskService() {
        return new TaskService();
    }

    @Bean
    public SchedulerController schedulerController() {
        return new SchedulerController();
    }

}
