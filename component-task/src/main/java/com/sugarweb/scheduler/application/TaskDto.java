package com.sugarweb.scheduler.application;

import lombok.Data;

/**
 * SchedulerTaskDto
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class TaskDto {

    private String id;

    private String taskName;

    private String cron;

    private String beanName;

    private String methodName;

    private String params;

    private String status;

}
