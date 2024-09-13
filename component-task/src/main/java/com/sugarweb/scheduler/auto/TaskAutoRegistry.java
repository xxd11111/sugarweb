package com.sugarweb.scheduler.auto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.auto.AbstractAutoRegistry;
import com.sugarweb.framework.common.Flag;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.scheduler.domain.TaskPo;
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
public class TaskAutoRegistry extends AbstractAutoRegistry<TaskPo> {

    protected void insert(TaskPo o) {
        Db.save(o);
    }


    protected void merge(TaskPo db, TaskPo scan) {
    }


    protected void deleteByCondition(Collection<TaskPo> collection) {
        List<String> removeTaskId = new ArrayList<>();
        List<TaskPo> all = Db.list(TaskPo.class);
        for (TaskPo task : all) {
            if (collection.stream().noneMatch(a -> task.getTaskName().equals(a.getTaskName()))) {
                removeTaskId.add(task.getId());
            }
        }
        Db.removeByIds(removeTaskId, TaskPo.class);
    }


    protected TaskPo selectOne(TaskPo o) {
        return Db.getOne(new LambdaQueryWrapper<TaskPo>()
                .eq(TaskPo::getTaskName, o.getTaskName()));
    }


    public Collection<TaskPo> scan() {
        Map<String, Object> beansWithAnnotation = BeanUtil.getBeansWithAnnotation(InnerTaskBean.class);
        Map<String, TaskPo> schedulerTaskMap = new HashMap<>();
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
                    TaskPo old = schedulerTaskMap.get(taskName);
                    throw new FrameworkException("存在同名taskName:{}, fist:{}, second:{}", old.getBeanName() + "." + old.getMethodName(), beanName + "." + method.getName());
                }

                TaskPo task = new TaskPo();
                task.setTaskName(taskName);
                task.setBeanName(beanName);
                task.setMethodName(method.getName());
                task.setParamsLength(paramsLength);
                task.setParams(params);
                task.setCron(cron);
                task.setStatus(Flag.TRUE.getValue());
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
