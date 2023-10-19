package com.sugarcoat.support.scheduler;

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

    // String[] getParamsInfo();
    //
    // String getLastExecuteTime();
    //
    // String getNextExecuteTime();
    //
    // String getRetryStrategy();
    //
    // String getRetryTimes();
    //
    // String getDelayStrategy();
    //
    // String getDelayTime();

    String getExecuteStatus();

    String getSchedulerStatus();

}
