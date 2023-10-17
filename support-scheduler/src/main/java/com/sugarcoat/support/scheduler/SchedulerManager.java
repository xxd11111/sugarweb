package com.sugarcoat.support.scheduler;

import jakarta.annotation.Resource;
import org.quartz.*;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/10/17
 */
public class SchedulerManager {

    @Resource
    private Scheduler scheduler;

    public void add(SchedulerInfo schedulerInfo) throws SchedulerException {
        scheduler.checkExists(new JobKey(schedulerInfo.getTaskName(), schedulerInfo.getTaskGroup()));
        JobDetail jobDetail = JobBuilder
                .newJob(SchedulerBean.class)
                .withIdentity(schedulerInfo.getTaskName(), schedulerInfo.getTaskGroup())
                .build();
        // 构建Cron调度器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(schedulerInfo.getCron())
                .withMisfireHandlingInstructionDoNothing();
        // 任务触发器
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(schedulerInfo.getTriggerName(), schedulerInfo.getTriggerGroup())
                .withSchedule(scheduleBuilder)
                .build();
        jobDetail.getJobDataMap().put("1", schedulerInfo);
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
