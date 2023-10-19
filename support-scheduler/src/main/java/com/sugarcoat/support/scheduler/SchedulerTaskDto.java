package com.sugarcoat.support.scheduler;

import lombok.Data;

/**
 * SchedulerTaskDto
 *
 * @author 许向东
 * @date 2023/10/18
 */
@Data
public class SchedulerTaskDto {

    private String taskName;

    private String triggerName;

    private String cron;

    private String executeStatus;

    private String schedulerStatus;

}
