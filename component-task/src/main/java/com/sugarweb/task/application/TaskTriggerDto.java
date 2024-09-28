package com.sugarweb.task.application;


import lombok.Data;

/**
 * TriggerDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class TaskTriggerDto {

    private String triggerId;

    private String taskId;

    private String triggerName;

    private String cron;

    private String enabled;

    private String triggerData;
}
