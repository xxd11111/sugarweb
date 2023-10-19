package com.sugarcoat.support.scheduler;

import com.sugarcoat.protocol.exception.FrameworkException;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TaskBeanFactory 实现类，交由spring管理
 *
 * @author 许向东
 * @date 2023/10/19
 */
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
        Set<String> taskNames = new HashSet<>();
        for (Map.Entry<String, Object> stringObjectEntry : beansWithAnnotation.entrySet()) {
            String beanName = stringObjectEntry.getKey();
            Object beanInstance = stringObjectEntry.getValue();
            Method[] methods = beanInstance.getClass().getMethods();
            for (Method method : methods) {
                InnerTaskMethod annotation = method.getAnnotation(InnerTaskMethod.class);
                boolean enable = annotation.enable();
                String[] params = annotation.params();
                String taskName = annotation.taskName();
                String cron = annotation.cron();
                if (cron.isEmpty()) {
                    enable = false;
                }
                SgcSchedulerTask schedulerTask = new SgcSchedulerTask();
                schedulerTask.setTaskName(taskName);
                schedulerTask.setBeanName(beanName);
                schedulerTask.setMethodName(method.getName());
                schedulerTask.setCron(cron);
                schedulerTask.setTriggerName(taskName);
                schedulerTask.setSchedulerStatus(enable ? "1" : "0");
                schedulerTask.setParams(params);
                if (taskNames.contains(taskName)) {
                    throw new FrameworkException("存在同名taskName:{}", taskName);
                }else {
                    taskNames.add(taskName);
                }
                if (schedulerManager.exists(taskName)) {
                    //todo 只更新方法名相关
                    // schedulerManager.update(schedulerTask);
                }else {
                    schedulerManager.add(schedulerTask);
                }
            }
        }


    }


}
