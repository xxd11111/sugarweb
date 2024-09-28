package com.sugarweb.task.infra;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.Flag;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.task.domain.po.TaskInfo;
import com.sugarweb.task.domain.po.TaskTrigger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.List;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class QuartzTaskManager implements TaskManager {

    private final Scheduler scheduler;

    @Override
    public void reload() {
        try {
            scheduler.clear();
        } catch (SchedulerException ex) {
            throw new FrameworkException("定时任务clear失败", ex);
        }
        List<TaskInfo> taskInfos = Db.list(new LambdaQueryWrapper<>(TaskInfo.class)
                .eq(TaskInfo::getEnabled, Flag.TRUE.getCode()));
        List<TaskTrigger> triggers = Db.list(new LambdaQueryWrapper<>(TaskTrigger.class)
                .eq(TaskTrigger::getEnabled, Flag.TRUE.getCode())
        );
        for (TaskInfo taskInfo : taskInfos) {
            loadTask(taskInfo, false);
        }
        for (TaskTrigger taskTrigger : triggers) {
            loadTrigger(taskTrigger, false);
        }
    }

    @Override
    public void saveTask(TaskInfo taskInfo) {
        Db.save(taskInfo);
        if (Flag.TRUE.getCode().equals(taskInfo.getEnabled())) {
            loadTask(taskInfo, false);
        }
    }

    private void loadTask(TaskInfo taskInfo, boolean replace) {
        JobDetail jobDetail = JobBuilder
                .newJob(QuartzTaskBeanAdapter.class)
                .withIdentity(taskInfo.getTaskId())
                .storeDurably(true)
                .build();
        try {
            scheduler.addJob(jobDetail, replace);
        } catch (SchedulerException ex) {
            throw new FrameworkException("定时任务addJob失败", ex);
        }
    }

    private void cleanTask(String taskId) {
        try {
            scheduler.deleteJob(JobKey.jobKey(taskId));
        } catch (SchedulerException ex) {
            throw new FrameworkException("定时任务deleteJob失败", ex);
        }
    }


    @Override
    public void updateTask(TaskInfo taskInfo) {
        Db.updateById(taskInfo);
        if (Flag.TRUE.getCode().equals(taskInfo.getEnabled())) {
            loadTask(taskInfo, true);
        } else {
            cleanTask(taskInfo.getTaskId());
        }
    }

    @Override
    public void removeTask(String taskId) {
        Db.removeById(taskId, TaskInfo.class);
        cleanTask(taskId);
    }

    @Override
    public void enabledTask(String taskId) {
        TaskInfo taskInfo = Db.getById(taskId, TaskInfo.class);
        taskInfo.setEnabled(Flag.TRUE.getCode());
        Db.updateById(taskInfo);
        loadTask(taskInfo, true);
    }

    @Override
    public void disabledTask(String taskId) {
        TaskInfo taskInfo = Db.getById(taskId, TaskInfo.class);
        taskInfo.setEnabled(Flag.FALSE.getCode());
        Db.updateById(taskInfo);
        cleanTask(taskId);
    }

    @Override
    public void saveTrigger(TaskTrigger taskTrigger) {
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        Db.save(taskTrigger);
        if (shouldRun(taskInfo.getEnabled(), taskTrigger.getEnabled())) {
            loadTrigger(taskTrigger, false);
        }
    }

    @Override
    public void updateTrigger(TaskTrigger taskTrigger) {
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        Db.updateById(taskTrigger);
        if (shouldRun(taskInfo.getEnabled(), taskTrigger.getEnabled())) {
            loadTrigger(taskTrigger, true);
        } else {
            clearTrigger(taskTrigger.getTriggerId());
        }
    }

    @Override
    public void saveTaskTrigger(TaskInfo taskInfo, TaskTrigger taskTrigger) {
        saveTask(taskInfo);
        saveTrigger(taskTrigger);
    }


    @Override
    public void removeTrigger(String triggerId) {
        Db.removeById(triggerId, TaskTrigger.class);
        clearTrigger(triggerId);
    }

    @Override
    public void enabledTrigger(String triggerId) {
        TaskTrigger taskTrigger = Db.getById(triggerId, TaskTrigger.class);
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        taskTrigger.setEnabled(Flag.TRUE.getCode());
        Db.updateById(taskTrigger);
        if (shouldRun(taskInfo.getEnabled(), taskTrigger.getEnabled())) {
            loadTrigger(taskTrigger, true);
        }
    }

    //todo  有问题
    private void loadTrigger(TaskTrigger taskTrigger, boolean replace) {
        // 构建Cron调度器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(taskTrigger.getCron())
                .withMisfireHandlingInstructionDoNothing();
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .forJob(taskTrigger.getTaskId())
                .withIdentity(taskTrigger.getTriggerId())
                .withSchedule(scheduleBuilder)
                .build();
        try {
            if (replace) {
                scheduler.rescheduleJob(TriggerKey.triggerKey(taskTrigger.getTriggerId()), trigger);
            } else {
                scheduler.scheduleJob(trigger);
            }
        } catch (SchedulerException ex) {
            throw new FrameworkException("定时任务loadTrigger失败", ex);
        }
    }

    private void clearTrigger(String triggerId) {
        try {
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerId));
        } catch (SchedulerException ex) {
            throw new FrameworkException("定时任务pauseTrigger失败", ex);
        }
    }

    private boolean shouldRun(String taskEnabled, String triggerEnabled) {
        return Flag.TRUE.getCode().equals(taskEnabled) && Flag.TRUE.getCode().equals(triggerEnabled);
    }

    @Override
    public void disabledTrigger(String triggerId) {
        TaskTrigger taskTrigger = Db.getById(triggerId, TaskTrigger.class);
        taskTrigger.setEnabled(Flag.FALSE.getCode());
        Db.updateById(taskTrigger);
        clearTrigger(triggerId);
    }

    @Override
    public void runTaskOnce(String beanName) {
        TaskBeanFactory.getTaskBean(beanName).run();
    }

}
