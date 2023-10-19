package com.sugarcoat.support.scheduler;

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
        schedulerTask.setTriggerName(dto.getTriggerName());
        schedulerTask.setCron(dto.getCron());
        schedulerTask.setSchedulerStatus(dto.getSchedulerStatus());
        schedulerManager.add(schedulerTask);
    }

    public void update(SchedulerTaskDto dto) {
        SgcSchedulerTask schedulerTask = new SgcSchedulerTask();
        schedulerTask.setTaskName(dto.getTaskName());
        schedulerTask.setTriggerName(dto.getTriggerName());
        schedulerTask.setCron(dto.getCron());
        schedulerTask.setSchedulerStatus(dto.getSchedulerStatus());
        schedulerManager.update(schedulerTask);
    }

    public void delete(String name) {
        schedulerManager.delete(name);
    }

    public void enable(String name) {
        schedulerManager.updateStatus(name, "1");
    }

    public void disable(String name) {
        schedulerManager.updateStatus(name, "0");
    }

    public void pause(String name) {
        schedulerManager.pause(name);
    }

    public void resume(String name) {
        schedulerManager.resume(name);
    }

    public void run(String name) {
        schedulerManager.run(name);
    }

    public void interrupt(String name) {
        schedulerManager.interrupt(name);
    }

    public List<SchedulerTaskDto> getAll() {
        List<SchedulerTask> all = schedulerManager.getAll();
        return all.stream()
                .map(a -> {
                    SchedulerTaskDto schedulerTaskDto = new SchedulerTaskDto();
                    schedulerTaskDto.setTaskName(a.getTaskName());
                    schedulerTaskDto.setTriggerName(a.getTriggerName());
                    schedulerTaskDto.setCron(a.getCron());
                    schedulerTaskDto.setSchedulerStatus(a.getSchedulerStatus());
                    schedulerTaskDto.setExecuteStatus(a.getExecuteStatus());
                    return schedulerTaskDto;
                })
                .collect(Collectors.toList());
    }

}
