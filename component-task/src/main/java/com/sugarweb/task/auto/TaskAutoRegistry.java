package com.sugarweb.task.auto;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.Flag;
import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.task.domain.TaskInfo;
import com.sugarweb.task.domain.TaskTrigger;
import org.springframework.scheduling.support.CronExpression;

import java.util.Map;

/**
 * TaskAutoRegistry
 *
 * @author 许向东
 * @version 1.0
 */
public class TaskAutoRegistry {

    public void run() throws Exception {
        Map<String, Object> beansWithAnnotation = BeanUtil.getBeansWithAnnotation(InnerTask.class);

        for (Map.Entry<String, Object> stringObjectEntry : beansWithAnnotation.entrySet()) {
            String beanName = stringObjectEntry.getKey();
            Object beanInstance = stringObjectEntry.getValue();
            InnerTask innerTask = beanInstance.getClass().getAnnotation(InnerTask.class);
            TaskInfo taskInfo = Db.getOne(new LambdaQueryWrapper<>(TaskInfo.class)
                    .eq(TaskInfo::getTaskCode, innerTask.taskCode())
                    .eq(TaskInfo::getIsDefault, Flag.TRUE.getCode())
            );
            if (taskInfo == null) {
                taskInfo = new TaskInfo();
                taskInfo.setTaskCode(innerTask.taskCode());
                taskInfo.setTaskName(innerTask.taskName());
                taskInfo.setBeanName(beanName);
                taskInfo.setMethodName(innerTask.taskName());
                taskInfo.setEnabled(innerTask.enabled() ? Flag.TRUE.getCode() : Flag.FALSE.getCode());
                taskInfo.setIsDefault(Flag.TRUE.getCode());
                Db.save(taskInfo);
            }

            InnerTaskTrigger innerTaskTrigger = beanInstance.getClass().getAnnotation(InnerTaskTrigger.class);
            if (innerTaskTrigger != null) {
                TaskTrigger trigger = Db.getOne(new LambdaQueryWrapper<>(TaskTrigger.class)
                        .eq(TaskTrigger::getIsDefault, Flag.TRUE.getCode())
                        .eq(TaskTrigger::getTaskId, taskInfo.getTaskId())
                        .eq(TaskTrigger::getTriggerCode, innerTaskTrigger.triggerCode()));
                if (trigger == null) {
                    TaskTrigger taskTrigger = new TaskTrigger();
                    taskTrigger.setTaskId(taskInfo.getTaskId());
                    taskTrigger.setTriggerCode(innerTaskTrigger.triggerCode());
                    if (!isValidCron(innerTaskTrigger.cron())) {
                        throw new IllegalArgumentException("Cron表达式不合法");
                    }
                    taskTrigger.setCron(innerTaskTrigger.cron());
                    taskTrigger.setEnabled(innerTaskTrigger.enabled() ? Flag.TRUE.getCode() : Flag.FALSE.getCode());
                    if (StrUtil.isEmpty(innerTaskTrigger.triggerName())) {
                        taskTrigger.setTriggerName(beanName + "-" + innerTaskTrigger.triggerCode());
                    } else {
                        taskTrigger.setTriggerName(innerTaskTrigger.triggerName());
                    }
                    taskTrigger.setIsDefault(Flag.TRUE.getCode());
                    Db.save(taskTrigger);
                }
            }
        }
    }

    private boolean isValidCron(String cron) {
        return CronExpression.isValidExpression(cron);
    }

}
