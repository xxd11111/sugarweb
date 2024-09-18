package com.sugarweb.task;

import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.task.application.TaskService;
import com.sugarweb.task.controller.TaskController;
import com.sugarweb.task.infra.TaskManager;
import com.sugarweb.task.infra.SingleTaskManager;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

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
