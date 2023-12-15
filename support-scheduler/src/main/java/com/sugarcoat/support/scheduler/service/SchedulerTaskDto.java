package com.sugarcoat.support.scheduler.service;

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

    private String cron;

    private String defaultCron;

    private String executeStatus;

    private String beanName;

    private String methodName;

    private Integer paramsLength;

    private String params;

    private String defaultParams;

}
