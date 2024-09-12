package com.sugarweb.scheduler.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.scheduler.domain.TaskInfo;

/**
 * TaskService
 *
 * @author 许向东
 * @version 1.0
 */
public class TaskService {

    public void save(TaskDto dto) {
        TaskInfo task = new TaskInfo();
        task.setTaskName(dto.getTaskName());
        task.setBeanName(dto.getBeanName());
        task.setMethodName(dto.getMethodName());
        task.setCron(dto.getCron());
        task.setParams(dto.getParams());
        task.setStatus(dto.getStatus());
        Db.save(task);
    }

    public void update(TaskDto dto) {
        TaskInfo task = Db.getById(dto.getId(), TaskInfo.class);
        task.setTaskName(dto.getTaskName());
        task.setBeanName(dto.getBeanName());
        task.setMethodName(dto.getMethodName());
        task.setCron(dto.getCron());
        task.setParams(dto.getParams());
        task.setStatus(dto.getStatus());
        Db.updateById(task);
    }

    public void remove(String id) {
        Db.removeById(id);
    }


    public void pause(String id) {
        //todo
    }


    public void resume(String id) {
        //todo
    }


    public void run(String id) {
        //todo
    }

    public IPage<TaskDto> page(PageQuery pageQuery, TaskQuery queryDto) {
        return Db.page(PageHelper.getPage(pageQuery), new LambdaQueryWrapper<TaskInfo>()
                        .like(TaskInfo::getTaskName, queryDto.getTaskName())
                        .eq(TaskInfo::getStatus, queryDto.getStatus()))
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
