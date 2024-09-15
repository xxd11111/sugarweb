package com.sugarweb.scheduler.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 调用记录
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class TaskTriggerLog {

    private String logId;
    private String taskId;
    private String taskCode;
    private String taskName;
    private String beanName;

    private String triggerId;
    private String triggerName;
    private String cron;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer costTime;
    private String executeResult;
    private String exceptionMessage;
    private String traceMessage;
}
