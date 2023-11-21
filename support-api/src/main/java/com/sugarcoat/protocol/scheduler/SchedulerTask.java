package com.sugarcoat.protocol.scheduler;

/**
 * 定时任务信息
 *
 * @author 许向东
 * @date 2023/10/17
 */
public interface SchedulerTask {

    String getTaskName();

    String getTriggerName();

    String getCron();

    String getParams();

    String getExecuteStatus();

}
