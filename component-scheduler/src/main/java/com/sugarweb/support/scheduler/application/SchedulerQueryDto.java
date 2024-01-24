package com.sugarweb.support.scheduler.application;

import lombok.Data;

/**
 * 定时任务查询
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class SchedulerQueryDto {

    private String taskName;

    private String status;

}
