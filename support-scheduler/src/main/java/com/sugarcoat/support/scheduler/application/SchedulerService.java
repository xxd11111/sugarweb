package com.sugarcoat.support.scheduler.application;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;

/**
 * SchedulerService
 *
 * @author 许向东
 * @date 2023/12/20
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
