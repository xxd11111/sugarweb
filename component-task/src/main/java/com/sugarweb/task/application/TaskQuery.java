package com.sugarweb.task.application;

import lombok.Data;

/**
 * 定时任务查询
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class TaskQuery {

    private String taskName;

    private String status;

}
