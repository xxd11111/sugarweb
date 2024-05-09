package com.sugarweb.scheduler.auto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.framework.auto.AbstractAutoRegistry;
import com.sugarweb.scheduler.domain.SchedulerTask;
import com.sugarweb.scheduler.domain.SchedulerTaskRepository;
import com.sugarweb.framework.common.BooleanFlag;
import org.springframework.scheduling.support.CronExpression;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * SchedulerAutoRegistry
 *
 * @author 许向东
 * @version 1.0
 */
public class SchedulerAutoRegistry extends AbstractAutoRegistry<SchedulerTask> {

    private final SchedulerTaskRepository sgcSchedulerTaskRepository;

    public SchedulerAutoRegistry(SchedulerTaskRepository sgcSchedulerTaskRepository) {
        this.sgcSchedulerTaskRepository = sgcSchedulerTaskRepository;
    }

    @Override
    protected void insert(SchedulerTask o) {
        sgcSchedulerTaskRepository.save(o);
    }

    @Override
    protected void merge(SchedulerTask db, SchedulerTask scan) {
        db.setDefaultCron(scan.getDefaultCron());
        db.setDefaultParams(scan.getDefaultParams());
        db.setBeanName(scan.getDefaultParams());
        db.setMethodName(scan.getDefaultParams());
        sgcSchedulerTaskRepository.save(db);
    }

    @Override
    protected void deleteByCondition(Collection<SchedulerTask> collection) {
        List<String> removeTaskId = new ArrayList<>();
        Iterable<SchedulerTask> all = sgcSchedulerTaskRepository.selectList();
        for (SchedulerTask schedulerTask : all) {
            if (collection.stream().noneMatch(a -> schedulerTask.getTaskName().equals(a.getTaskName()))) {
                removeTaskId.add(schedulerTask.getId());
            }
        }
        sgcSchedulerTaskRepository.deleteBatchIds(removeTaskId);
    }

    @Override
    protected SchedulerTask selectOne(SchedulerTask o) {
        return sgcSchedulerTaskRepository.selectOne(new LambdaQueryWrapper<SchedulerTask>()
                .eq(SchedulerTask::getTaskName, o.getTaskName()));
    }

    @Override
    public Collection<SchedulerTask> scan() {
        Map<String, Object> beansWithAnnotation = BeanUtil.getBeansWithAnnotation(InnerTaskBean.class);
        Map<String, SchedulerTask> schedulerTaskMap = new HashMap<>();
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
                    SchedulerTask old = schedulerTaskMap.get(taskName);
                    throw new FrameworkException("存在同名taskName:{}, fist:{}, second:{}", old.getBeanName() + "." + old.getMethodName(), beanName + "." + method.getName());
                }

                SchedulerTask schedulerTask = new SchedulerTask();
                schedulerTask.setTaskName(taskName);
                schedulerTask.setBeanName(beanName);
                schedulerTask.setMethodName(method.getName());
                schedulerTask.setParamsLength(paramsLength);
                schedulerTask.setParams(params);
                schedulerTask.setDefaultParams(params);
                schedulerTask.setCron(cron);
                schedulerTask.setDefaultCron(cron);
                schedulerTask.setStatus(BooleanFlag.TRUE.getCode());
                schedulerTaskMap.put(taskName, schedulerTask);
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
