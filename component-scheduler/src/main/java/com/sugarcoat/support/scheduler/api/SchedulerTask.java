package com.sugarcoat.support.scheduler.api;

/**
 * 定时任务信息
 *
 * @author 许向东
 * @date 2023/10/17
 */
public interface SchedulerTask {

    String getTaskName();

    String getCron();

    String getParams();

}
