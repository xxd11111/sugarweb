package com.sugarweb.scheduler;

import com.sugarweb.scheduler.application.TaskService;
import com.sugarweb.scheduler.controller.TaskController;
import com.sugarweb.scheduler.infra.TaskManager;
import com.sugarweb.scheduler.infra.SingleTaskManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务自动注入
 *
 * @author xxd
 * @version 1.0
 */
//不使用spring的定时任务
// @EnableScheduling
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SchedulerProperties.class)
// @ConditionalOnProperty(prefix = "sugarweb.scheduler", name = "enable", havingValue = "true")
public class SchedulerAutoConfiguration {

    @Bean
    public SingleTaskManager taskManager() {
        SingleTaskManager taskManager = new SingleTaskManager();
        taskManager.initLoad();
        return taskManager;
    }

    @Bean
    public TaskService taskService(TaskManager taskManager) {
        return new TaskService(taskManager);
    }

    @Bean
    public TaskController schedulerController() {
        return new TaskController();
    }

}
