package com.sugarcoat.support.scheduler;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Trigger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * TaskBeanFactory 实现类，交由spring管理
 *
 * @author 许向东
 * @date 2023/10/19
 */
@Slf4j
public class SgcTaskBeanFactory implements TaskBeanFactory, TaskBeanRegistry, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final SchedulerManager schedulerManager;

    public SgcTaskBeanFactory(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object getBean(String taskName) {
        return applicationContext.getBean(taskName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(InnerTaskBean.class);
        Map<String, SgcSchedulerTask> schedulerTaskMap = new HashMap<>();
        for (Map.Entry<String, Object> stringObjectEntry : beansWithAnnotation.entrySet()) {
            String beanName = stringObjectEntry.getKey();
            Object beanInstance = stringObjectEntry.getValue();
            Method[] methods = beanInstance.getClass().getMethods();
            for (Method method : methods) {
                InnerTaskMethod annotation = method.getAnnotation(InnerTaskMethod.class);
                if (annotation == null) {
                    continue;
                }
                String params = annotation.params();
                String taskName = annotation.taskName();
                if (taskName == null || taskName.isEmpty()) {
                    taskName = beanName + "." + method.getName();
                }
                String cron = annotation.cron();
                if (!isValidCron(cron)) {
                    throw new FrameworkException("cron表达式错误，请检查{}注解中的cron是否正确", beanName + "." + method.getName());
                }
                Parameter[] parameters = method.getParameters();
                if (!isParameterStringOrNull(parameters)) {
                    throw new FrameworkException("schedulerTask方法不符合要求，只允许无参数或者一个String参数");
                }
                int paramsLength = parameters.length;
                if (schedulerTaskMap.containsKey(taskName)) {
                    SgcSchedulerTask old = schedulerTaskMap.get(taskName);
                    throw new FrameworkException("存在同名taskName:{}, fist:{}, second:{}", old.getBeanName() + "." + old.getMethodName(), beanName + "." + method.getName());
                }

                SgcSchedulerTask schedulerTask = new SgcSchedulerTask();
                schedulerTask.setTaskName(taskName);
                schedulerTask.setBeanName(beanName);
                schedulerTask.setMethodName(method.getName());
                schedulerTask.setParamsLength(paramsLength);
                schedulerTask.setCustomParams(params);
                schedulerTask.setDefaultParams(params);
                schedulerTask.setCustomCron(cron);
                schedulerTask.setDefaultCron(cron);
                schedulerTask.setTriggerName(taskName);
                schedulerTaskMap.put(taskName, schedulerTask);

                //如果想强制全部更新，建议定时任务代码变动时更新taskName（此方案是为了重新部署防止自定义数据还原）
                if (schedulerManager.exists(taskName)) {
                    SgcSchedulerTask old = (SgcSchedulerTask)schedulerManager.getOne(taskName);
                    //保留旧的参数方法情况，修改过
                    if (!StrUtil.equals(old.getCustomCron(), old.getDefaultCron())) {
                        schedulerTask.setCustomCron(old.getParams());
                    }
                    //保留旧的cron方法情况，修改过
                    if (!StrUtil.equals(old.getCron(), old.getDefaultCron())) {
                        schedulerTask.setCustomCron(old.getCustomCron());
                    }
                    schedulerManager.update(schedulerTask);
                    if (Trigger.TriggerState.PAUSED.name().equals(old.getExecuteStatus())){
                        schedulerManager.pause(old.getTaskName());
                    }
                } else {
                    schedulerManager.add(schedulerTask);
                }
            }
        }
        log.info("已加载{}个定时任务", schedulerTaskMap.size());
        schedulerTaskMap.forEach((k, v) -> log.info("定时任务，taskName：{}，{}", k, v));
    }

    private boolean isValidCron(String cron) {
        return CronExpression.isValidExpression(cron);
    }

    private boolean isParameterStringOrNull(Parameter[] parameters) {
        if (parameters.length == 0) {
            return true;
        }
        if (parameters.length == 1) {
            return parameters[0].getType().getName().equals("java.lang.String");
        }
        return false;
    }


}
