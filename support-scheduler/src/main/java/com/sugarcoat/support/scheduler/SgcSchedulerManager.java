package com.sugarcoat.support.scheduler;

import jakarta.annotation.Resource;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @date 2023/10/17
 */
public class SgcSchedulerManager implements SchedulerManager {

    @Resource
    private Scheduler scheduler;

    public void add(SchedulerTask schedulerTask) {
        JobDetail jobDetail = JobBuilder
                .newJob(SchedulerJobBean.class)
                .withIdentity(schedulerTask.getTaskName())
                .build();
        // 构建Cron调度器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(schedulerTask.getCron())
                .withMisfireHandlingInstructionDoNothing();
        // 任务触发器
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(schedulerTask.getTriggerName())
                .withSchedule(scheduleBuilder)
                .build();
        jobDetail.getJobDataMap().put("1", schedulerTask);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(SchedulerTask schedulerTask) {
        try {
            // 查询触发器Key
            TriggerKey triggerKey = new TriggerKey(schedulerTask.getTriggerName());
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
            throw new RuntimeException("updateJob Fail", e);
        }
    }

    @Override
    public void enable(String name) {
        updateStatus(name, "1");
    }

    @Override
    public void disable(String name) {
        updateStatus(name, "0");
    }

    public void resume(String name) {
        try {
            scheduler.resumeJob(JobKey.jobKey(name));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void pause(String name) {
        try {
            scheduler.pauseJob(JobKey.jobKey(name));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String name) {
        try {
            scheduler.deleteJob(JobKey.jobKey(name));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void interrupt(String name) {
        try {
            scheduler.interrupt(JobKey.jobKey(name));
        } catch (UnableToInterruptJobException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists(String name) {
        try {
            return scheduler.checkExists(JobKey.jobKey(name));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(String name) {
        try {
            scheduler.triggerJob(JobKey.jobKey(name), null);
        } catch (SchedulerException e) {
            throw new RuntimeException("run Fail", e);
        }
    }

    @Override
    public void updateStatus(String name, String status) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(name));
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            SgcSchedulerTask schedulerTask = (SgcSchedulerTask) jobDataMap.get("1");
            schedulerTask.setSchedulerStatus(status);
            jobDataMap.put("1", schedulerTask);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SchedulerTask> getAll() {
        List<SchedulerTask> sgcSchedulerTasks = new ArrayList<>();
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyGroup());
            for (JobKey jobKey : jobKeys) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                SgcSchedulerTask sgcSchedulerTask = (SgcSchedulerTask) jobDetail.getJobDataMap().get("1");
                sgcSchedulerTasks.add(sgcSchedulerTask);
            }
            return sgcSchedulerTasks;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
