package com.sugarcoat.support.scheduler.application;

import com.xxd.common.PageData;
import com.xxd.common.PageDto;

/**
 * SchedulerService
 *
 * @author 许向东
 * @version 1.0
 */
public interface SchedulerService {

    void add(SchedulerTaskDto dto);

    void update(SchedulerTaskDto dto);

    void delete(String id);

    void pause(String id);

    void resume(String id);

    void run(String id);

    PageData<SchedulerTaskDto> page(PageDto pageDto, SchedulerQueryDto queryDto);

}
