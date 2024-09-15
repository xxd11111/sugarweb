package com.sugarweb.scheduler.infra;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.Flag;
import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.scheduler.po.TaskInfo;
import com.sugarweb.scheduler.po.TaskTrigger;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * TaskManagerImpl
 *
 * @author 许向东
 * @version 1.0
 */
public class SingleTaskManager extends SimpleAsyncTaskScheduler implements TaskManager {

    private final AtomicBoolean isInit = new AtomicBoolean(false);

    private final SimpleAsyncTaskScheduler taskScheduler = this;

    private final Map<String, ScheduledFuture<?>> runningTriggers = new ConcurrentHashMap<>();

    public void saveTask(TaskInfo taskInfo) {
        Db.save(taskInfo);
    }

    public void updateTask(TaskInfo taskInfo) {
        List<TaskTrigger> taskTriggers = Db.list(new LambdaQueryWrapper<TaskTrigger>()
                .eq(TaskTrigger::getEnabled, Flag.TRUE.getCode())
                .eq(TaskTrigger::getTaskId, taskInfo.getTaskId()));
        for (TaskTrigger taskTrigger : taskTriggers) {
            if (shouldRun(taskInfo.getEnabled(), taskTrigger.getEnabled())) {
                cleanTrigger(taskTrigger.getTriggerId());
                loadTrigger(taskTrigger.getTriggerId(), taskInfo.getBeanName(), taskTrigger.getCron());
            } else {
                cleanTrigger(taskTrigger.getTriggerId());
            }
        }
        Db.updateById(taskInfo);
    }

    private boolean shouldRun(String taskEnabled, String triggerEnabled) {
        return taskEnabled.equals(Flag.TRUE.getCode()) && triggerEnabled.equals(Flag.TRUE.getCode());
    }

    public void saveTrigger(TaskTrigger taskTrigger) {
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        if (shouldRun(taskInfo.getEnabled(), taskTrigger.getEnabled())) {
            cleanTrigger(taskTrigger.getTriggerId());
            loadTrigger(taskTrigger.getTriggerId(), taskInfo.getBeanName(), taskTrigger.getCron());
        }
        Db.save(taskTrigger);
    }

    public void updateTrigger(TaskTrigger taskTrigger) {
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        if (shouldRun(taskInfo.getEnabled(), taskTrigger.getEnabled())) {
            cleanTrigger(taskTrigger.getTriggerId());
            loadTrigger(taskTrigger.getTriggerId(), taskInfo.getBeanName(), taskTrigger.getCron());
        }
        Db.updateById(taskTrigger);
    }

    public void saveTaskTrigger(TaskInfo taskInfo, TaskTrigger taskTrigger) {
        saveTask(taskInfo);
        saveTrigger(taskTrigger);
    }

    public void removeTask(String taskCode) {
        List<TaskTrigger> triggers = Db.list(new LambdaQueryWrapper<TaskTrigger>().eq(TaskTrigger::getTaskId, taskCode));
        if (CollUtil.isNotEmpty(triggers)) {
            for (TaskTrigger trigger : triggers) {
                cleanTrigger(trigger.getTriggerId());
            }
        }
        Db.remove(new LambdaQueryWrapper<TaskTrigger>().eq(TaskTrigger::getTaskCode, taskCode));
        Db.removeById(taskCode, TaskInfo.class);
    }

    public void removeTrigger(String triggerId) {
        cleanTrigger(triggerId);
        Db.removeById(triggerId, TaskTrigger.class);
    }

    public void disabledTrigger(String triggerId) {
        cleanTrigger(triggerId);
        Db.update(new LambdaUpdateWrapper<TaskTrigger>()
                .set(TaskTrigger::getEnabled, Flag.FALSE.getCode())
                .eq(TaskTrigger::getTriggerId, triggerId));
    }

    private void loadTrigger(String triggerId, String beanName, String cron) {
        BaseTask baseTask = new BaseTask(beanName);
        ScheduledFuture<?> schedule = taskScheduler.schedule(baseTask::execute, new CronTrigger(cron));
        runningTriggers.put(triggerId, schedule);
    }

    private void cleanTrigger(String triggerId) {
        if (runningTriggers.containsKey(triggerId)) {
            ScheduledFuture<?> scheduledFuture = runningTriggers.get(triggerId);
            scheduledFuture.cancel(true);
            runningTriggers.remove(triggerId);
        }
    }

    public void enabledTrigger(String triggerId) {
        TaskTrigger taskTrigger = Db.getById(triggerId, TaskTrigger.class);
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        if (shouldRun(taskInfo.getEnabled(), taskTrigger.getEnabled())) {
            cleanTrigger(triggerId);
            loadTrigger(taskTrigger.getCron(), taskInfo.getBeanName(), taskTrigger.getCron());
        }
        Db.update(new LambdaUpdateWrapper<TaskTrigger>()
                .set(TaskTrigger::getEnabled, Flag.TRUE.getCode())
                .eq(TaskTrigger::getTriggerId, triggerId));
    }

    @Override
    public void runTaskOnce(String beanName) {
        new BaseTask(beanName).execute();
    }

    public void init() {
        if (isInit.compareAndSet(false, true)) {
            TaskBeanAdapter.load(BeanUtil.getBeansOfType(TaskBean.class));

            List<TaskInfo> taskInfos = Db.list(new LambdaQueryWrapper<TaskInfo>()
                    .eq(TaskInfo::getEnabled, Flag.TRUE.getCode()));
            Map<String, String> taskIdBeanNameMap = taskInfos.stream()
                    .collect(Collectors.toMap(TaskInfo::getTaskId, TaskInfo::getBeanName));

            List<TaskTrigger> triggers = Db.list(new LambdaQueryWrapper<TaskTrigger>()
                    .eq(TaskTrigger::getEnabled, Flag.TRUE.getCode())
            );

            for (TaskTrigger trigger : triggers) {
                String beanName = taskIdBeanNameMap.get(trigger.getTaskId());
                ScheduledFuture<?> schedule = taskScheduler.schedule(new BaseTask(beanName)::execute, new CronTrigger(trigger.getCron()));
                runningTriggers.put(trigger.getTriggerId(), schedule);
            }
        } else {
            throw new IllegalStateException("TaskManager 已经初始化");
        }

    }

}
