package com.sugarweb.task;

import com.sugarweb.task.application.TaskService;
import com.sugarweb.task.infra.auto.TaskAutoRegistry;
import com.sugarweb.task.controller.TaskController;
import com.sugarweb.task.infra.QuartzTaskManager;
import com.sugarweb.task.infra.SpringbootTaskAdapter;
import com.sugarweb.task.infra.TaskManager;
import jakarta.annotation.Resource;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

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

    @Resource
    private TaskProperties taskProperties;

    @Bean
    public TaskAutoRegistry taskAutoRegistry() {
        return new TaskAutoRegistry(taskProperties);
    }

    @Bean
    public SpringbootTaskAdapter springbootTaskAdapter() {
        return new SpringbootTaskAdapter();
    }

    @Bean
    public TaskManager taskManager(Scheduler scheduler) {
        return new QuartzTaskManager(scheduler);
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
