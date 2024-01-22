package com.sugarcoat.support.scheduler.service;

import lombok.Data;

/**
 * 定时任务查询
 *
 * @author 许向东
 * @date 2023/12/21
 */
@Data
public class SchedulerQueryDto {

    private String taskName;

    private String status;

}
