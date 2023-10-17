package com.sugarcoat.support.scheduler;

/**
 * 定时任务信息
 *
 * @author 许向东
 * @date 2023/10/17
 */
public interface SchedulerInfo {

    String getTaskName();

    String getTaskGroup();

    String getTriggerName();

    String getTriggerGroup();

    String getCron();

    String getBeanName();

    String getMethodName();

    Object[] getParams();

    String[] getParamsInfo();

    String getLastExecuteTime();

    String getNextExecuteTime();

    String getRetryStrategy();

    String getRetryTimes();

    String getDelayStrategy();

    String getDelayTime();

    String getExecuteStatus();

    String getTaskStatus();

}
