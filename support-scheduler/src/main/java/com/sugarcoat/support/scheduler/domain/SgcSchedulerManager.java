package com.sugarcoat.support.scheduler.domain;

import com.sugarcoat.protocol.JsonUtil;
import com.sugarcoat.protocol.scheduler.SchedulerManager;
import com.sugarcoat.protocol.scheduler.SchedulerTask;
import jakarta.annotation.Resource;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.*;

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
                .forJob(jobDetail)
                .withIdentity(schedulerTask.getTaskName())
                .withSchedule(scheduleBuilder)
                .build();

        trigger.getJobDataMap().put("1", JsonUtil.toJsonStr(schedulerTask));
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(SchedulerTask schedulerTask) {
        try {
            // 查询触发器Key
            TriggerKey triggerKey = new TriggerKey(schedulerTask.getTaskName());
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
            trigger.getJobDataMap().put("1", JsonUtil.toJsonStr(schedulerTask));
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("updateJob Fail", e);
        }
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

    public boolean exists(String name) {
        try {
            return scheduler.checkExists(TriggerKey.triggerKey(name));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(SchedulerTask schedulerTask) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("1", JsonUtil.toJsonStr(schedulerTask));
            scheduler.triggerJob(JobKey.jobKey(schedulerTask.getTaskName()), jobDataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException("run Fail", e);
        }
    }

    @Override
    public List<SchedulerTask> getAll() {
        List<SchedulerTask> sgcSchedulerTasks = new ArrayList<>();
        try {
            Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.anyGroup());
            for (TriggerKey triggerKey : triggerKeys) {
                Trigger trigger = scheduler.getTrigger(triggerKey);
                String sgcSchedulerTaskString = (String) trigger.getJobDataMap().get("1");
                SgcSchedulerTask sgcSchedulerTask = JsonUtil.toObject(sgcSchedulerTaskString, SgcSchedulerTask.class);                Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
                sgcSchedulerTask.setExecuteStatus(triggerState.name());
                sgcSchedulerTasks.add(sgcSchedulerTask);
            }
            return sgcSchedulerTasks;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SchedulerTask getOne(String name) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            String sgcSchedulerTaskString = (String) trigger.getJobDataMap().get("1");
            SgcSchedulerTask sgcSchedulerTask = JsonUtil.toObject(sgcSchedulerTaskString, SgcSchedulerTask.class);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            sgcSchedulerTask.setExecuteStatus(triggerState.name());
            return sgcSchedulerTask;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
