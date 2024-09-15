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

    private String taskId;

    private String taskCode;

    private String taskName;

    private String beanName;

    private String methodName;

    private String enabled;

    private String taskData;
}
