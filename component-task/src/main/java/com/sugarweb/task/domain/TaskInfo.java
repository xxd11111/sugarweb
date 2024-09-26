package com.sugarweb.task.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * TaskInfo
 *
 * @author 许向东
 * @version 1.0
 */
@Data
@TableName("")
public class TaskInfo {

    @TableId
    private String taskId;

    private String taskCode;

    private String taskName;

    private String beanName;

    private String enabled;

    private String taskData;

    private String isDefault;

}
