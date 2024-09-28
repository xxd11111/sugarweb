package com.sugarweb.task.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * TaskTrigger
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class TaskTrigger {

    @TableId
    private String triggerId;

    private String taskId;

    private String triggerCode;

    private String triggerName;

    private String cron;

    private String enabled;

    private String triggerData;

    private String isDefault;

}
