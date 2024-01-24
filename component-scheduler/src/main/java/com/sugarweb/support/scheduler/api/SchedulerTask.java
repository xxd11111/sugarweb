package com.sugarweb.support.scheduler.api;

/**
 * 定时任务信息
 *
 * @author 许向东
 * @version 1.0
 */
public interface SchedulerTask {

    String getTaskName();

    String getCron();

    String getParams();

}
