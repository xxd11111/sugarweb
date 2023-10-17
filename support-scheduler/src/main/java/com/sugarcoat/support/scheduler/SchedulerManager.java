package com.sugarcoat.support.scheduler;

import jakarta.annotation.Resource;
import org.quartz.*;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @date 2023/10/17
 */
public class SchedulerManager {

    @Resource
    private Scheduler scheduler;

    public void add(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getTaskName(), schedulerInfo.getTaskGroup());
        JobDetail jobDetail = JobBuilder
                .newJob(SchedulerBean.class)
                .withIdentity(jobKey)
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

    public void resume(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getTaskName(), schedulerInfo.getTaskGroup());
        scheduler.resumeJob(jobKey);
    }

    public void pause(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getTaskName(), schedulerInfo.getTaskGroup());
        scheduler.pauseJob(jobKey);
    }

    public void delete(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getTaskName(), schedulerInfo.getTaskGroup());
        scheduler.deleteJob(jobKey);
    }

    public void interrupt(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getTaskName(), schedulerInfo.getTaskGroup());
        scheduler.interrupt(jobKey);
    }

    public boolean exists(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getTaskName(), schedulerInfo.getTaskGroup());
        return scheduler.checkExists(jobKey);
    }

}
