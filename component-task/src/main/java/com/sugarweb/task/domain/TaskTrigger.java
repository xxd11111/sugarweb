package com.sugarweb.task.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * TaskTrigger
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class TaskTrigger {

    @TableId
    private String triggerId;

    private String taskId;

    private String taskCode;

    private String triggerName;

    private String cron;

    private String enabled;

    private String triggerData;

}
