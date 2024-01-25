package com.sugarweb.scheduler.application;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageRequest;

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

    PageData<SchedulerTaskDto> page(PageRequest pageRequest, SchedulerQueryDto queryDto);

}
