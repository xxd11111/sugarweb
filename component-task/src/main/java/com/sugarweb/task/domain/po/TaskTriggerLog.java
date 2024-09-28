package com.sugarweb.task.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 调用记录
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class TaskTriggerLog {

    @TableId
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
