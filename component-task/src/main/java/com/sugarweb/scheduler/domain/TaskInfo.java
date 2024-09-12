package com.sugarweb.scheduler.domain;

import lombok.Data;

/**
 * SgcSchedulerTask
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class TaskInfo {

    private String id;

    private String taskName;

    private String beanName;

    private String methodName;

    private String cron;

    private String status;

    private Integer paramsLength;

    private String params;

    // private String lastExecuteTime;
    //
    // private String nextExecuteTime;
    //
    // private String retryStrategy;
    //
    // private String retryTimes;
    //
    // private String delayStrategy;
    //
    // private String delayTime;

}
