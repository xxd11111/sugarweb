package com.sugarweb.scheduler.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.scheduler.domain.TaskPo;
import com.sugarweb.scheduler.domain.TaskStatus;
import com.sugarweb.scheduler.infra.SwTask;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * TaskService
 *
 * @author 许向东
 * @version 1.0
 */
public class TaskService {

    private final SimpleAsyncTaskScheduler taskScheduler;

    private final Map<String, ScheduledFuture<?>> runningTasks = new ConcurrentHashMap<>();

    private void saveTask(TaskPo taskPo) {
        String id = taskPo.getId();
        if (runningTasks.containsKey(id)) {
            throw new IllegalArgumentException("任务已存在");
        }
        SwTask taskBean = getTaskBean(taskPo.getBeanName());
        if (taskBean == null) {
            throw new IllegalArgumentException("bean不存在");
        }
        ScheduledFuture<?> future = taskScheduler.schedule(taskBean::execute, new CronTrigger(taskPo.getCron()));
        runningTasks.put(id, future);
    }

    public void updateTask(TaskPo taskPo) {
        stopTask(taskPo.getId());
        saveTask(taskPo);
    }

    private void stopTask(String id) {
        if (runningTasks.containsKey(id)) {
            ScheduledFuture<?> scheduledFuture = runningTasks.get(id);
            scheduledFuture.cancel(true);
            runningTasks.remove(id);
        }
    }

    public boolean existsBean(String beanName) {
        return getTaskBean(beanName) != null;
    }

    private SwTask getTaskBean(String beanName) {
        try {
            return BeanUtil.getBean(beanName, SwTask.class);
        } catch (Exception ignored) {
        }
        return null;
    }

    public void reload() {
        List<TaskPo> list = Db.list(TaskPo.class);
        taskScheduler.stop();
        taskScheduler.start();
        list.forEach(this::saveTask);
    }


    public void save(TaskDto dto) {
        TaskPo task = new TaskPo();
        task.setTaskName(dto.getTaskName());
        task.setBeanName(dto.getBeanName());
        task.setMethodName(dto.getMethodName());
        task.setCron(dto.getCron());
        task.setParams(dto.getParams());
        task.setStatus(dto.getStatus());
        Db.save(task);

        if (TaskStatus.RUNNING.getCode().equals(task.getStatus())) {
            SwTask taskBean = getTaskBean(task.getBeanName());
            ScheduledFuture<?> future = taskScheduler.schedule(taskBean::execute, new CronTrigger(task.getCron()));
            runningTasks.put(task.getId(), future);

            task.setStatus(TaskStatus.BEAN_NOT_FOUND.getCode());
            Db.updateById(task);
        }
    }


    public void update(TaskDto dto) {
        TaskPo task = Db.getById(dto.getId(), TaskPo.class);
        task.setTaskName(dto.getTaskName());
        task.setBeanName(dto.getBeanName());
        task.setMethodName(dto.getMethodName());
        task.setCron(dto.getCron());
        task.setParams(dto.getParams());
        task.setStatus(dto.getStatus());
        Db.updateById(task);

        stopTask(task.getId());
        if (TaskStatus.RUNNING.getCode().equals(task.getStatus())) {
            SwTask taskBean = getTaskBean(task.getBeanName());
            ScheduledFuture<?> future = taskScheduler.schedule(taskBean::execute, new CronTrigger(task.getCron()));
            runningTasks.put(task.getId(), future);

            task.setStatus(TaskStatus.BEAN_NOT_FOUND.getCode());
            Db.updateById(task);
        }
    }


    public void remove(String id) {
        Db.removeById(id);
        stopTask(id);
    }


    public void stop(String id) {
        TaskPo task = Db.getById(id, TaskPo.class);
        task.setStatus(TaskStatus.STOP.getCode());
        Db.updateById(task);
        stopTask(id);
    }


    public void start(String id) {
        TaskPo task = Db.getById(id, TaskPo.class);
        task.setStatus(TaskStatus.RUNNING.getCode());
        Db.updateById(task);
        SwTask bean = getTaskBean(task.getBeanName());
        if (bean != null) {
            putTask(id, bean, task.getCron());
        }
    }


    public void run(String id) {
        TaskPo task = Db.getById(id, TaskPo.class);
        SwTask bean = getTaskBean(task.getBeanName());
        if (bean != null) {
            bean.execute();
        }
    }

    public IPage<TaskDto> page(PageQuery pageQuery, TaskQuery queryDto) {
        return Db.page(PageHelper.getPage(pageQuery), new LambdaQueryWrapper<TaskPo>()
                        .like(TaskPo::getTaskName, queryDto.getTaskName())
                        .eq(TaskPo::getStatus, queryDto.getStatus()))
                .convert(a -> {
                    TaskDto taskDto = new TaskDto();
                    taskDto.setId(a.getId());
                    taskDto.setTaskName(a.getTaskName());
                    taskDto.setBeanName(a.getBeanName());
                    taskDto.setMethodName(a.getMethodName());
                    taskDto.setCron(a.getCron());
                    taskDto.setParams(a.getParams());
                    taskDto.setStatus(a.getStatus());
                    return taskDto;
                });
    }
}
