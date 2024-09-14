package com.sugarweb.scheduler.infra;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.stream.Collectors;

/**
 * TaskManagerImpl
 *
 * @author 许向东
 * @version 1.0
 */
public class SingleTaskManager extends SimpleAsyncTaskScheduler implements TaskManager {

    private final SimpleAsyncTaskScheduler taskScheduler = this;

    private final Map<String, ScheduledFuture<?>> runningTriggers = new ConcurrentHashMap<>();

    private Map<String, TaskBean> taskBeanMap;

    public void saveTask(TaskInfo taskInfo) {
        Db.save(taskInfo);
    }

    public void updateTask(TaskInfo taskInfo) {
        Db.updateById(taskInfo);
    }

    public void saveTrigger(TaskTrigger taskTrigger) {
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        Db.save(taskTrigger);

        TaskBean taskBean = getTaskBean(taskInfo.getBeanName());
        if (taskBean != null) {
            ScheduledFuture<?> schedule = taskScheduler.schedule(taskBean::execute, new CronTrigger(taskTrigger.getCron()));
            runningTriggers.put(taskTrigger.getTriggerId(), schedule);
        }
    }

    public void updateTrigger(TaskTrigger taskTrigger) {
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        Db.updateById(taskTrigger);

        stopTrigger(taskTrigger.getTriggerId());

        TaskBean taskBean = getTaskBean(taskInfo.getBeanName());
        if (taskBean != null) {
            ScheduledFuture<?> schedule = taskScheduler.schedule(taskBean::execute, new CronTrigger(taskTrigger.getCron()));
            runningTriggers.put(taskTrigger.getTriggerId(), schedule);
        }
    }

    public void saveTaskTrigger(TaskInfo taskInfo, TaskTrigger taskTrigger) {
        saveTask(taskInfo);
        saveTrigger(taskTrigger);
    }

    public void removeTask(String taskId) {
        List<TaskTrigger> triggers = Db.list(new LambdaQueryWrapper<TaskTrigger>().eq(TaskTrigger::getTaskId, taskId));
        if (CollUtil.isNotEmpty(triggers)) {
            for (TaskTrigger trigger : triggers) {
                stopTrigger(trigger.getTriggerId());
            }
        }
        Db.remove(new LambdaQueryWrapper<TaskTrigger>().eq(TaskTrigger::getTaskId, taskId));
        Db.removeById(taskId, TaskInfo.class);
    }

    public void removeTrigger(String triggerId) {
        stopTrigger(triggerId);
        Db.removeById(triggerId, TaskTrigger.class);
    }

    public void stopTrigger(String triggerId) {
        if (runningTriggers.containsKey(triggerId)) {
            ScheduledFuture<?> scheduledFuture = runningTriggers.get(triggerId);
            scheduledFuture.cancel(true);
            runningTriggers.remove(triggerId);
        }
    }

    public void startTrigger(String triggerId) {
        if (runningTriggers.containsKey(triggerId)) {
            return;
        }
        TaskTrigger taskTrigger = Db.getById(triggerId, TaskTrigger.class);
        TaskInfo taskInfo = Db.getById(taskTrigger.getTaskId(), TaskInfo.class);
        TaskBean taskBean = getTaskBean(taskInfo.getBeanName());
        if (taskBean != null) {
            ScheduledFuture<?> schedule = taskScheduler.schedule(taskBean::execute, new CronTrigger(taskTrigger.getCron()));
            runningTriggers.put(taskTrigger.getTriggerId(), schedule);
        }
    }

    @Override
    public void runTaskOnce(String beanName) {
        TaskBean taskBean = getTaskBean(beanName);
        if (taskBean != null) {
            taskBean.execute();
        }
    }

    public boolean existsBean(String beanName) {
        return taskBeanMap.containsKey(beanName);
    }

    public TaskBean getTaskBean(String beanName) {
        return taskBeanMap.get(beanName);
    }

    public List<String> getTaskBeanNames() {
        return CollUtil.newArrayList(taskBeanMap.keySet());
    }

    public void initLoad() {
        taskBeanMap = BeanUtil.getBeansOfType(TaskBean.class);

        List<TaskInfo> taskInfos = Db.list(new LambdaQueryWrapper<TaskInfo>()
                .eq(TaskInfo::getEnabled, Flag.TRUE.getCode()));
        Map<String, String> taskIdBeanNameMap = taskInfos.stream()
                .collect(Collectors.toMap(TaskInfo::getTaskId, TaskInfo::getBeanName));

        List<TaskTrigger> triggers = Db.list(new LambdaQueryWrapper<TaskTrigger>()
                .eq(TaskTrigger::getEnabled, Flag.TRUE.getCode())
        );

        for (TaskTrigger trigger : triggers) {
            String beanName = taskIdBeanNameMap.get(trigger.getTaskId());
            if (beanName != null) {
                TaskBean taskBean = getTaskBean(beanName);
                ScheduledFuture<?> schedule = taskScheduler.schedule(taskBean::execute, new CronTrigger(trigger.getCron()));
                runningTriggers.put(trigger.getTriggerId(), schedule);
            }
        }
    }

}
