package com.sugarweb.scheduler;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

public class SpringTaskConfiguration implements SchedulingConfigurer {

    private final SimpleAsyncTaskScheduler scheduler;

    public SpringTaskConfiguration(SimpleAsyncTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                () -> System.out.println("定时任务1!"),
                a -> new CronTrigger("cron1").nextExecution(a)
        );
        taskRegistrar.setTaskScheduler(scheduler);
    }

}
