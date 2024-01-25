package com.sugarweb.scheduler.application;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageRequest;
import com.sugarweb.scheduler.domain.SchedulerTask;
import com.sugarweb.scheduler.infra.SchedulerManager;
import com.sugarweb.scheduler.domain.QSchedulerTask;
import com.sugarweb.scheduler.domain.SchedulerTaskRepository;
import com.sugarweb.framework.orm.ExpressionWrapper;
import com.sugarweb.framework.orm.PageDataConvert;
import org.springframework.data.domain.Page;

/**
 * SchedulerService
 *
 * @author 许向东
 * @version 1.0
 */
public class SchedulerServiceImpl implements SchedulerService {

    private final SchedulerManager schedulerManager;

    private final SchedulerTaskRepository sgcSchedulerTaskRepository;

    public SchedulerServiceImpl(SchedulerManager schedulerManager, SchedulerTaskRepository sgcSchedulerTaskRepository) {
        this.schedulerManager = schedulerManager;
        this.sgcSchedulerTaskRepository = sgcSchedulerTaskRepository;
    }

    @Override
    public void add(SchedulerTaskDto dto) {
        SchedulerTask schedulerTask = new SchedulerTask();
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
        SchedulerTask schedulerTask = (SchedulerTask) schedulerManager.getOne(dto.getId());
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
    public PageData<SchedulerTaskDto> page(PageRequest pageDto, SchedulerQueryDto queryDto) {
        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize());
        QSchedulerTask sgcSchedulerTask = QSchedulerTask.schedulerTask;
        BooleanExpression build = ExpressionWrapper.of()
                .and(Strings.isNullOrEmpty(queryDto.getTaskName()), sgcSchedulerTask.taskName.like(queryDto.getTaskName()))
                .and(Strings.isNullOrEmpty(queryDto.getStatus()), sgcSchedulerTask.status.like(queryDto.getStatus()))
                .build();

        Page<SchedulerTaskDto> page = sgcSchedulerTaskRepository.findAll(build, pageRequest).map(a -> {
            SchedulerTaskDto schedulerTaskDto = new SchedulerTaskDto();
            schedulerTaskDto.setId(a.getId());
            schedulerTaskDto.setTaskName(a.getTaskName());
            schedulerTaskDto.setBeanName(a.getBeanName());
            schedulerTaskDto.setMethodName(a.getMethodName());
            schedulerTaskDto.setParamsLength(a.getParamsLength());
            schedulerTaskDto.setCron(a.getCron());
            schedulerTaskDto.setDefaultCron(a.getDefaultCron());
            schedulerTaskDto.setParams(a.getParams());
            schedulerTaskDto.setDefaultParams(a.getDefaultParams());
            schedulerTaskDto.setStatus(a.getStatus());
            return schedulerTaskDto;
        });
        return PageDataConvert.convert(page, SchedulerTaskDto.class);
    }

}
