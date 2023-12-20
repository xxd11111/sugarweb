package com.sugarcoat.support.scheduler.service;

import com.sugarcoat.protocol.scheduler.SchedulerManager;
import com.sugarcoat.protocol.scheduler.SchedulerTask;
import com.sugarcoat.support.scheduler.domain.SgcSchedulerTask;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SchedulerService
 *
 * @author 许向东
 * @date 2023/10/18
 */
public class SchedulerServiceImpl implements SchedulerService{

    private final SchedulerManager schedulerManager;

    public SchedulerServiceImpl(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    @Override
    public void add(SchedulerTaskDto dto) {
        SgcSchedulerTask schedulerTask = new SgcSchedulerTask();
        schedulerTask.setTaskName(dto.getTaskName());
        schedulerTask.setBeanName(dto.getBeanName());
        schedulerTask.setMethodName(dto.getMethodName());
        schedulerTask.setParamsLength(dto.getParamsLength());
        schedulerTask.setCron(dto.getCron());
        schedulerTask.setDefaultCron(dto.getDefaultCron());
        schedulerTask.setParams(dto.getParams());
        schedulerTask.setDefaultParams(dto.getDefaultParams());
        schedulerTask.setStatus(dto.getStatus());
        schedulerManager.add(schedulerTask);
    }

    @Override
    public void update(SchedulerTaskDto dto) {
        SgcSchedulerTask schedulerTask = (SgcSchedulerTask) schedulerManager.getOne(dto.getId());
        schedulerTask.setTaskName(dto.getTaskName());
        schedulerTask.setBeanName(dto.getBeanName());
        schedulerTask.setMethodName(dto.getMethodName());
        schedulerTask.setParamsLength(dto.getParamsLength());
        schedulerTask.setCron(dto.getCron());
        schedulerTask.setParams(dto.getParams());
        schedulerTask.setStatus(dto.getStatus());
        schedulerManager.update(schedulerTask);
    }

    @Override
    public void delete(String id) {
        schedulerManager.delete(id);
    }

    @Override
    public void pause(String id) {
        schedulerManager.pause(id);
    }

    @Override
    public void resume(String id) {
        schedulerManager.resume(id);
    }

    @Override
    public void run(String id) {
        schedulerManager.run(id);
    }

    @Override
    public List<SchedulerTaskDto> getAll() {
        List<SchedulerTask> all = schedulerManager.getAll();
        return all.stream()
                .map(a -> {
                    SgcSchedulerTask sst = (SgcSchedulerTask) a;
                    SchedulerTaskDto schedulerTaskDto = new SchedulerTaskDto();
                    schedulerTaskDto.setId(sst.getId());
                    schedulerTaskDto.setTaskName(sst.getTaskName());
                    schedulerTaskDto.setBeanName(sst.getBeanName());
                    schedulerTaskDto.setMethodName(sst.getMethodName());
                    schedulerTaskDto.setParamsLength(sst.getParamsLength());
                    schedulerTaskDto.setCron(sst.getCron());
                    schedulerTaskDto.setDefaultCron(sst.getDefaultCron());
                    schedulerTaskDto.setParams(sst.getParams());
                    schedulerTaskDto.setDefaultParams(sst.getDefaultParams());
                    schedulerTaskDto.setStatus(sst.getStatus());
                    return schedulerTaskDto;
                })
                .collect(Collectors.toList());
    }

}
