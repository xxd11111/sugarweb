package com.sugarcoat.support.scheduler;

import org.quartz.Trigger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SchedulerService
 *
 * @author 许向东
 * @date 2023/10/18
 */
public class SchedulerService {

    private final SchedulerManager schedulerManager;

    public SchedulerService(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public void add(SchedulerTaskDto dto) {
        SgcSchedulerTask schedulerTask = new SgcSchedulerTask();
        schedulerTask.setTaskName(dto.getTaskName());
        schedulerTask.setBeanName(dto.getBeanName());
        schedulerTask.setMethodName(dto.getMethodName());
        schedulerTask.setParamsLength(dto.getParamsLength());
        schedulerTask.setTriggerName(dto.getTriggerName());
        schedulerTask.setCustomCron(dto.getCron());
        schedulerTask.setDefaultCron(dto.getDefaultCron());
        schedulerTask.setCustomParams(dto.getParams());
        schedulerTask.setDefaultParams(dto.getDefaultParams());
        schedulerTask.setExecuteStatus(Trigger.TriggerState.NORMAL.name());
        schedulerManager.add(schedulerTask);
    }

    public void update(SchedulerTaskDto dto) {
        SgcSchedulerTask schedulerTask = (SgcSchedulerTask)schedulerManager.getOne(dto.getTaskName());
        schedulerTask.setTaskName(dto.getTaskName());
        schedulerTask.setBeanName(dto.getBeanName());
        schedulerTask.setMethodName(dto.getMethodName());
        schedulerTask.setParamsLength(dto.getParamsLength());
        schedulerTask.setTriggerName(dto.getTriggerName());
        schedulerTask.setCustomCron(dto.getCron());
        schedulerTask.setCustomParams(dto.getParams());
        schedulerManager.update(schedulerTask);
    }

    public void delete(String name) {
        schedulerManager.delete(name);
    }

    public void pause(String name) {
        schedulerManager.pause(name);
    }

    public void resume(String name) {
        schedulerManager.resume(name);
    }

    public void run(SchedulerTaskDto dto) {
        SgcSchedulerTask schedulerTask = new SgcSchedulerTask();
        schedulerTask.setTaskName(dto.getTaskName());
        schedulerTask.setBeanName(dto.getBeanName());
        schedulerTask.setMethodName(dto.getMethodName());
        schedulerTask.setParamsLength(dto.getParamsLength());
        schedulerTask.setTriggerName(dto.getTriggerName());
        schedulerTask.setCustomCron(dto.getCron());
        schedulerTask.setDefaultCron(dto.getDefaultCron());
        schedulerTask.setCustomParams(dto.getParams());
        schedulerTask.setDefaultParams(dto.getDefaultParams());
        schedulerManager.run(schedulerTask);
    }

    public List<SchedulerTaskDto> getAll() {
        List<SchedulerTask> all = schedulerManager.getAll();
        return all.stream()
                .map(a -> {
                    SgcSchedulerTask sst = (SgcSchedulerTask)a;
                    SchedulerTaskDto schedulerTaskDto = new SchedulerTaskDto();
                    schedulerTaskDto.setTaskName(sst.getTaskName());
                    schedulerTaskDto.setBeanName(sst.getBeanName());
                    schedulerTaskDto.setMethodName(sst.getMethodName());
                    schedulerTaskDto.setParamsLength(sst.getParamsLength());
                    schedulerTaskDto.setTriggerName(sst.getTriggerName());
                    schedulerTaskDto.setCron(sst.getCustomCron());
                    schedulerTaskDto.setDefaultCron(sst.getDefaultCron());
                    schedulerTaskDto.setParams(sst.getCustomParams());
                    schedulerTaskDto.setDefaultParams(sst.getDefaultParams());
                    schedulerTaskDto.setExecuteStatus(sst.getExecuteStatus());
                    return schedulerTaskDto;
                })
                .collect(Collectors.toList());
    }

}
