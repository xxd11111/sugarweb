package com.sugarweb.support.scheduler.application;

import lombok.Data;

/**
 * SchedulerTaskDto
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class SchedulerTaskDto {

    private String id;

    private String taskName;

    private String cron;

    private String defaultCron;

    private String beanName;

    private String methodName;

    private Integer paramsLength;

    private String params;

    private String defaultParams;

    private String status;

}
