package com.sugarweb.task.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.task.domain.TaskInfo;
import com.sugarweb.task.domain.TaskTrigger;
import com.sugarweb.task.infra.TaskManager;
import lombok.RequiredArgsConstructor;

/**
 * TaskService
 *
 * @author 许向东
 * @version 1.0
 */
@RequiredArgsConstructor
public class TaskService {

    private final TaskManager taskManager;

    public void reload() {
        taskManager.reload();
    }

    public void saveTask(TaskDto dto) {
        TaskInfo task = new TaskInfo();
        task.setTaskId(dto.getTaskId());
        task.setTaskCode(dto.getTaskCode());
        task.setTaskName(dto.getTaskName());
        task.setBeanName(dto.getBeanName());
        task.setMethodName(dto.getMethodName());
        task.setEnabled(dto.getEnabled());
        task.setTaskData(dto.getTaskData());
        taskManager.saveTask(task);
    }


    public void updateTask(TaskDto dto) {
        TaskInfo task = Db.getById(dto.getTaskId(), TaskInfo.class);
        task.setTaskName(dto.getTaskName());
        task.setBeanName(dto.getBeanName());
        task.setMethodName(dto.getMethodName());
        task.setEnabled(dto.getEnabled());
        task.setTaskData(dto.getTaskData());
        taskManager.updateTask(task);
    }

    public void enabledTask(String taskId) {
        taskManager.enabledTask(taskId);
    }

    public void disabledTask(String taskId) {
        taskManager.disabledTask(taskId);
    }

    public void saveTrigger(TaskTriggerDto dto) {
        TaskTrigger taskTrigger = new TaskTrigger();
        taskTrigger.setTriggerId(dto.getTriggerId());
        taskTrigger.setTaskId(dto.getTaskId());
        taskTrigger.setTriggerName(dto.getTriggerName());
        taskTrigger.setCron(dto.getCron());
        taskTrigger.setEnabled(dto.getEnabled());
        taskTrigger.setTriggerData(dto.getTriggerData());
        taskManager.saveTrigger(taskTrigger);
    }

    public void updateTrigger(TaskTriggerDto dto) {
        TaskTrigger taskTrigger = Db.getById(dto.getTriggerId(), TaskTrigger.class);
        taskTrigger.setTriggerName(dto.getTriggerName());
        taskTrigger.setCron(dto.getCron());
        taskTrigger.setEnabled(dto.getEnabled());
        taskTrigger.setTriggerData(dto.getTriggerData());
        taskManager.updateTrigger(taskTrigger);
    }

    public void removeTask(String taskId) {
        taskManager.removeTask(taskId);
    }

    public void removeTrigger(String triggerId) {
        taskManager.removeTrigger(triggerId);
    }

    public void enabledTrigger(String triggerId) {
        taskManager.enabledTrigger(triggerId);
    }

    public void disabledTrigger(String triggerId) {
        taskManager.disabledTrigger(triggerId);
    }

    public void run(String beanName) {
        taskManager.runTaskOnce(beanName);
    }

    public IPage<TaskDto> page(PageQuery pageQuery, TaskQuery queryDto) {
        return Db.page(PageHelper.getPage(pageQuery), new LambdaQueryWrapper<TaskInfo>()
                        .like(TaskInfo::getTaskName, queryDto.getTaskName())
                        .eq(TaskInfo::getEnabled, queryDto.getStatus()))
                .convert(a -> {
                    TaskDto taskDto = new TaskDto();
                    taskDto.setTaskId(a.getTaskId());
                    taskDto.setTaskName(a.getTaskName());
                    taskDto.setBeanName(a.getBeanName());
                    taskDto.setMethodName(a.getMethodName());
                    taskDto.setEnabled(a.getEnabled());
                    taskDto.setTaskData(a.getTaskData());
                    return taskDto;
                });
    }
}
