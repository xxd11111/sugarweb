package com.sugarweb.task.infra.auto;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.Flag;
import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.task.TaskProperties;
import com.sugarweb.task.domain.po.TaskInfo;
import com.sugarweb.task.domain.po.TaskTrigger;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * TaskAutoRegistry
 *
 * @author xxd
 * @version 1.0
 */
public class TaskAutoRegistry {

    //是否开启自动注册
    private boolean enabled = false;
    //是否重置
    private boolean reset = false;
    //是否强制更新
    private boolean forceUpdate = false;

    public TaskAutoRegistry(TaskProperties taskProperties) {
        if (taskProperties.getAutoRegisterStrategy() == AutoRegisterStrategy.save) {
            enabled = true;
        } else if (taskProperties.getAutoRegisterStrategy() == AutoRegisterStrategy.saveAndUpdate) {
            enabled = true;
            forceUpdate = true;
        } else if (taskProperties.getAutoRegisterStrategy() == AutoRegisterStrategy.reset) {
            enabled = true;
            forceUpdate = true;
            reset = true;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void run() {
        //如果关闭自动注册，则直接返回
        if (!enabled) {
            return;
        }
        Map<String, Object> beansWithAnnotation = BeanUtil.getBeansWithAnnotation(InnerTask.class);
        //如果开启重置，则先清空生成的数据
        if (reset) {
            Db.remove(new LambdaQueryWrapper<>(TaskInfo.class)
                    .eq(TaskInfo::getIsDefault, Flag.TRUE.getCode()));
            Db.remove(new LambdaQueryWrapper<>(TaskTrigger.class)
                    .eq(TaskTrigger::getIsDefault, Flag.TRUE.getCode()));
        }
        //遍历所有带InnerTask注解的bean
        for (Map.Entry<String, Object> stringObjectEntry : beansWithAnnotation.entrySet()) {
            String beanName = stringObjectEntry.getKey();
            Object beanInstance = stringObjectEntry.getValue();
            InnerTask innerTask = beanInstance.getClass().getAnnotation(InnerTask.class);
            TaskInfo taskInfo = Db.getOne(new LambdaQueryWrapper<>(TaskInfo.class)
                    .eq(TaskInfo::getTaskCode, innerTask.taskCode())
                    .eq(TaskInfo::getIsDefault, Flag.TRUE.getCode())
            );
            //如果不存在，则创建
            if (taskInfo == null) {
                taskInfo = new TaskInfo();
                buildTaskInfo(taskInfo, innerTask, beanName);
                Db.save(taskInfo);
            } else {
                //如果存在，则判断是否需要更新
                if (forceUpdate) {
                    buildTaskInfo(taskInfo, innerTask, beanName);
                    Db.updateById(taskInfo);
                }
            }

            //遍历所有带InnerTaskTrigger注解的bean
            InnerTaskTrigger innerTaskTrigger = beanInstance.getClass().getAnnotation(InnerTaskTrigger.class);
            if (innerTaskTrigger != null) {
                TaskTrigger taskTrigger = Db.getOne(new LambdaQueryWrapper<>(TaskTrigger.class)
                        .eq(TaskTrigger::getIsDefault, Flag.TRUE.getCode())
                        .eq(TaskTrigger::getTaskId, taskInfo.getTaskId())
                        .eq(TaskTrigger::getTriggerCode, innerTaskTrigger.triggerCode()));
                //如果不存在，则创建
                if (taskTrigger == null) {
                    taskTrigger = new TaskTrigger();
                    buildTaskTrigger(taskTrigger, innerTaskTrigger, taskInfo);
                    Db.save(taskTrigger);
                } else {
                    //如果存在，则判断是否需要更新
                    if (forceUpdate) {
                        buildTaskTrigger(taskTrigger, innerTaskTrigger, taskInfo);
                        Db.updateById(taskTrigger);
                    }
                }
            }
        }
    }

    private void buildTaskInfo(TaskInfo taskInfo, InnerTask innerTask, String beanName) {
        if (StrUtil.isEmpty(innerTask.taskCode())) {
            throw new IllegalArgumentException("taskCode不能为空");
        }
        taskInfo.setTaskCode(innerTask.taskCode());
        if (StrUtil.isEmpty(innerTask.taskName())) {
            taskInfo.setTaskName(beanName);
        } else {
            taskInfo.setTaskName(innerTask.taskName());
        }
        taskInfo.setBeanName(beanName);
        taskInfo.setTaskName(innerTask.taskName());
        taskInfo.setEnabled(innerTask.enabled() ? Flag.TRUE.getCode() : Flag.FALSE.getCode());
        taskInfo.setIsDefault(Flag.TRUE.getCode());
    }

    private void buildTaskTrigger(TaskTrigger taskTrigger, InnerTaskTrigger innerTaskTrigger, TaskInfo taskInfo) {
        taskTrigger.setTaskId(taskInfo.getTaskId());
        if (StrUtil.isEmpty(innerTaskTrigger.triggerCode())) {
            throw new IllegalArgumentException("triggerCode不能为空");
        }
        taskTrigger.setTriggerCode(innerTaskTrigger.triggerCode());
        if (!isValidCron(innerTaskTrigger.cron())) {
            throw new IllegalArgumentException("Cron表达式不合法");
        }
        taskTrigger.setCron(innerTaskTrigger.cron());
        taskTrigger.setEnabled(innerTaskTrigger.enabled() ? Flag.TRUE.getCode() : Flag.FALSE.getCode());
        if (StrUtil.isEmpty(innerTaskTrigger.triggerName())) {
            taskTrigger.setTriggerName(taskInfo.getTaskName() + "-" + innerTaskTrigger.triggerCode());
        } else {
            taskTrigger.setTriggerName(innerTaskTrigger.triggerName());
        }
        taskTrigger.setIsDefault(Flag.TRUE.getCode());
    }

    private boolean isValidCron(String cron) {
        return CronExpression.isValidExpression(cron);
    }

}
