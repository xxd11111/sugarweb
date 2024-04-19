package com.sugarweb.scheduler.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;

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

    IPage<SchedulerTaskDto> page(PageQuery pageQuery, SchedulerQueryDto queryDto);

}
