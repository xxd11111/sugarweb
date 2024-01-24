package com.sugarweb.support.scheduler.auto;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.BeanUtil;
import com.sugarweb.exception.FrameworkException;
import com.sugarweb.support.scheduler.api.InnerTaskBean;
import com.sugarweb.support.scheduler.api.InnerTaskMethod;
import com.sugarweb.auto.AbstractAutoRegistry;
import com.sugarweb.support.scheduler.domain.QSgcSchedulerTask;
import com.sugarweb.support.scheduler.domain.SgcSchedulerTask;
import com.sugarweb.support.scheduler.domain.BaseSchedulerTaskRepository;
import com.sugarweb.orm.BooleanEnum;
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
public class SchedulerAutoRegistry extends AbstractAutoRegistry<SgcSchedulerTask> {

    private final BaseSchedulerTaskRepository sgcSchedulerTaskRepository;

    public SchedulerAutoRegistry(BaseSchedulerTaskRepository sgcSchedulerTaskRepository) {
        this.sgcSchedulerTaskRepository = sgcSchedulerTaskRepository;
    }

    @Override
    protected void insert(SgcSchedulerTask o) {
        sgcSchedulerTaskRepository.save(o);
    }

    @Override
    protected void merge(SgcSchedulerTask db, SgcSchedulerTask scan) {
        db.setDefaultCron(scan.getDefaultCron());
        db.setDefaultParams(scan.getDefaultParams());
        db.setBeanName(scan.getDefaultParams());
        db.setMethodName(scan.getDefaultParams());
        sgcSchedulerTaskRepository.save(db);
    }

    @Override
    protected void deleteByCondition(Collection<SgcSchedulerTask> collection) {
        List<String> removeTaskName = new ArrayList<>();
        Iterable<SgcSchedulerTask> all = sgcSchedulerTaskRepository.findAll();
        for (SgcSchedulerTask schedulerTask : all) {
            if (collection.stream().noneMatch(a -> schedulerTask.getTaskName().equals(a.getTaskName()))) {
                removeTaskName.add(schedulerTask.getTaskName());
            }
        }
        sgcSchedulerTaskRepository.deleteAllById(removeTaskName);
    }

    @Override
    protected SgcSchedulerTask selectOne(SgcSchedulerTask o) {
        QSgcSchedulerTask sgcSchedulerTask = QSgcSchedulerTask.sgcSchedulerTask;
        BooleanExpression eq = sgcSchedulerTask.taskName.eq(o.getTaskName());
        return sgcSchedulerTaskRepository.findOne(eq).orElse(null);
    }

    @Override
    public Collection<SgcSchedulerTask> scan() {
        Map<String, Object> beansWithAnnotation = BeanUtil.getBeansWithAnnotation(InnerTaskBean.class);
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
                schedulerTask.setParams(params);
                schedulerTask.setDefaultParams(params);
                schedulerTask.setCron(cron);
                schedulerTask.setDefaultCron(cron);
                schedulerTask.setStatus(BooleanEnum.TRUE.getValue());
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
