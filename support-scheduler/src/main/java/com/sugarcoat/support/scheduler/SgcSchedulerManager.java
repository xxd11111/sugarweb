package com.sugarcoat.support.scheduler;

import jakarta.annotation.Resource;
import org.quartz.*;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @date 2023/10/17
 */
public class SgcSchedulerManager implements SchedulerManager{

    @Resource
    private Scheduler scheduler;

    public void add(SchedulerTask schedulerTask) {
        JobKey jobKey = new JobKey(schedulerTask.getTaskName(), schedulerTask.getTaskGroup());
        JobDetail jobDetail = JobBuilder
                .newJob(SchedulerJobBean.class)
                .withIdentity(jobKey)
                .build();
        // 构建Cron调度器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(schedulerTask.getCron())
                .withMisfireHandlingInstructionDoNothing();
        // 任务触发器
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(schedulerTask.getSchedulerName(), schedulerTask.getSchedulerGroup())
                .withSchedule(scheduleBuilder)
                .build();
        jobDetail.getJobDataMap().put("1", schedulerTask);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(SchedulerTask schedulerTask){
        try {
            // 查询触发器Key
            TriggerKey triggerKey = new TriggerKey(schedulerTask.getSchedulerName(), schedulerTask.getSchedulerGroup());
            // 构建Cron调度器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(schedulerTask.getCron())
                    .withMisfireHandlingInstructionDoNothing();
            // 任务触发器
            Trigger trigger = ((CronTrigger) scheduler.getTrigger(triggerKey))
                    .getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
            trigger.getJobDataMap().put("1", schedulerTask);
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("updateJob Fail",e) ;
        }
    }

    public void resume(String name, String group) {
        JobKey jobKey = new JobKey(name, group);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void pause(String name, String group) {
        JobKey jobKey = new JobKey(name, group);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String name, String group) {
        JobKey jobKey = new JobKey(name, group);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void interrupt(String name, String group) {
        JobKey jobKey = new JobKey(name, group);
        try {
            scheduler.interrupt(jobKey);
        } catch (UnableToInterruptJobException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists(String name, String group) {
        JobKey jobKey = new JobKey(name, group);
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(SchedulerTask schedulerTask) {
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("1", schedulerTask);
            JobKey jobKey = new JobKey(schedulerTask.getTaskName(), schedulerTask.getTaskGroup());
            this.scheduler.triggerJob(jobKey, dataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException("run Fail", e);
        }
    }

}
