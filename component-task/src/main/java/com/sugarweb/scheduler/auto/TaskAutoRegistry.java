package com.sugarweb.scheduler.auto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.auto.AbstractAutoRegistry;
import com.sugarweb.framework.common.Flag;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.scheduler.po.TaskInfo;
import org.springframework.scheduling.support.CronExpression;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * TaskAutoRegistry
 *
 * @author 许向东
 * @version 1.0
 */
public class TaskAutoRegistry extends AbstractAutoRegistry<TaskInfo> {

    protected void insert(TaskInfo o) {
        Db.save(o);
    }


    protected void merge(TaskInfo db, TaskInfo scan) {
    }


    protected void deleteByCondition(Collection<TaskInfo> collection) {
        List<String> removeTaskId = new ArrayList<>();
        List<TaskInfo> all = Db.list(TaskInfo.class);
        for (TaskInfo task : all) {
            if (collection.stream().noneMatch(a -> task.getTaskName().equals(a.getTaskName()))) {
                removeTaskId.add(task.getTaskId());
            }
        }
        Db.removeByIds(removeTaskId, TaskInfo.class);
    }


    protected TaskInfo selectOne(TaskInfo o) {
        return Db.getOne(new LambdaQueryWrapper<TaskInfo>()
                .eq(TaskInfo::getTaskName, o.getTaskName()));
    }


    public Collection<TaskInfo> scan() {
        Map<String, Object> beansWithAnnotation = BeanUtil.getBeansWithAnnotation(RegistryTaskBean.class);
        Map<String, TaskInfo> schedulerTaskMap = new HashMap<>();
        for (Map.Entry<String, Object> stringObjectEntry : beansWithAnnotation.entrySet()) {
            String beanName = stringObjectEntry.getKey();
            Object beanInstance = stringObjectEntry.getValue();
            Method[] methods = beanInstance.getClass().getMethods();
            for (Method method : methods) {
                RegistryTaskTrigger annotation = method.getAnnotation(RegistryTaskTrigger.class);
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
                    TaskInfo old = schedulerTaskMap.get(taskName);
                    throw new FrameworkException("存在同名taskName:{}, fist:{}, second:{}", old.getBeanName() + "." + old.getMethodName(), beanName + "." + method.getName());
                }

                TaskInfo task = new TaskInfo();
                task.setTaskName(taskName);
                task.setBeanName(beanName);
                task.setMethodName(method.getName());
                task.setEnabled(Flag.TRUE.getCode());
                schedulerTaskMap.put(taskName, task);
            }
        }
        return schedulerTaskMap.values();
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
