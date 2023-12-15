package com.sugarcoat.support.scheduler;

import com.sugarcoat.protocol.scheduler.SchedulerManager;
import com.sugarcoat.support.scheduler.controller.SchedulerController;
import com.sugarcoat.support.scheduler.domain.SchedulerRunner;
import com.sugarcoat.support.scheduler.domain.SgcSchedulerManager;
import com.sugarcoat.support.scheduler.domain.SgcSchedulerTaskRepository;
import com.sugarcoat.support.scheduler.service.SchedulerService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务自动注入
 *
 * @author xxd
 * @since 2023/8/10
 */
@Configuration(proxyBeanMethods = false)
@EntityScan
@EnableConfigurationProperties(SchedulerProperties.class)
// @ConditionalOnProperty(prefix = "sugarcoat.scheduler", name = "enable", havingValue = "true")
public class SchedulerAutoConfiguration {

    @Bean
    public SchedulerManager schedulerManager() {
        return new SgcSchedulerManager();
    }

    @Bean
    public SchedulerService schedulerService(SchedulerManager schedulerManager) {
        return new SchedulerService(schedulerManager);
    }

    @Bean
    public SchedulerController schedulerController() {
        return new SchedulerController();
    }

    @Bean
    public SchedulerRunner schedulerRunner(SchedulerManager schedulerManager, SgcSchedulerTaskRepository sgcSchedulerTaskRepository) {
        return new SchedulerRunner(schedulerManager, sgcSchedulerTaskRepository);
    }

}
