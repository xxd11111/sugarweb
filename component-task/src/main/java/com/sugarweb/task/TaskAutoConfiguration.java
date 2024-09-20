package com.sugarweb.task;

import com.sugarweb.framework.FrameworkAutoConfiguration;
import com.sugarweb.task.application.TaskService;
import com.sugarweb.task.controller.TaskController;
import com.sugarweb.task.infra.SingleTaskManager;
import com.sugarweb.task.infra.TaskManager;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;

/**
 * 定时任务自动注入
 *
 * @author xxd
 * @version 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties(TaskProperties.class)
// @MapperScan({"com.sugarweb.task.mapper"})
@ConditionalOnProperty(prefix = "sugarweb.task", name = "enable", havingValue = "true", matchIfMissing = true)
public class TaskAutoConfiguration {

    @Bean
    public ApplicationRunner taskRunner(SingleTaskManager taskManager) {
        return args -> taskManager.init();
    }

    @Bean
    public SingleTaskManager taskManager() {
        SingleTaskManager taskManager = new SingleTaskManager();
        taskManager.setVirtualThreads(true);
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
