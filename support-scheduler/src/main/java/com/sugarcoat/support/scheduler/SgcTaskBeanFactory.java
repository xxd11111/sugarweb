package com.sugarcoat.support.scheduler;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.exception.FrameworkException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
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
                if (annotation == null){
                    continue;
                }
                boolean enable = annotation.enable();
                String[] params = annotation.params();
                String taskName = annotation.taskName();
                if (taskName == null || taskName.isEmpty()) {
                    taskName = beanName + "." + method.getName();
                }
                String cron = annotation.cron();
                if (!isValidCron(cron)) {
                    throw new FrameworkException("cron表达式错误，请检查{}注解中的cron是否正确", beanName + "." + method.getName());
                }
                Parameter[] parameters = method.getParameters();
                if (!isParamsMatch(parameters, params)) {
                    throw new FrameworkException("schedulerTask方法与预设参数不符合，请检查{}的参数与注解预设params是否匹配", beanName + "." + method.getName());
                }
                if (schedulerTaskMap.containsKey(taskName)) {
                    SgcSchedulerTask old = schedulerTaskMap.get(taskName);
                    throw new FrameworkException("存在同名taskName:{}, fist:{}, second:{}", old.getBeanName() + "." + old.getMethodName(), beanName + "." + method.getName());
                }

                SgcSchedulerTask schedulerTask = new SgcSchedulerTask();
                schedulerTask.setTaskName(taskName);
                schedulerTask.setBeanName(beanName);
                schedulerTask.setMethodName(method.getName());
                schedulerTask.setCron(cron);
                schedulerTask.setDefaultCron(cron);
                schedulerTask.setTriggerName(taskName);
                schedulerTask.setSchedulerStatus(enable ? "1" : "0");
                schedulerTask.setParams(params);
                schedulerTask.setDefaultParams(params);
                schedulerTaskMap.put(taskName, schedulerTask);

                //如果想强制全部更新，建议定时任务代码变动时更新taskName（此方案是为了重新部署防止改动数据还原）
                if (schedulerManager.exists(taskName)) {
                    SgcSchedulerTask old = (SgcSchedulerTask) schedulerManager.getOne(taskName);
                    //保留旧的参数方法情况，修改过
                    if (!isSameArray(old.getParams(), old.getDefaultParams())) {
                        if (isParamsMatch(parameters, old.getParams())) {
                            schedulerTask.setParams(old.getParams());
                        }
                    }
                    //保留旧的cron方法情况，修改过
                    if (!StrUtil.equals(old.getCron(), old.getDefaultCron())) {
                        schedulerTask.setCron(old.getCron());
                    }
                    schedulerManager.update(schedulerTask);
                } else {
                    schedulerManager.add(schedulerTask);
                }
            }
        }

    }

    private boolean isValidCron(String cron){
        //todo 验证cron是否有效
        return true;
    }

    private boolean isParamsMatch(Parameter[] parameters, String[] params) {

        if (parameters.length == params.length) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                String name = parameter.getName();
                //todo 验证参数类型 允许基本类型装箱类型及String
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isSameArray(String[] fist, String[] second) {
        if (fist == null) {
            return second == null || second.length == 0;
        }
        if (fist.length != second.length) {
            return false;
        }
        for (int i = 0; i < fist.length; i++) {
            if (!StrUtil.equals(fist[i], second[i])) {
                return false;
            }
        }
        return true;
    }


}
