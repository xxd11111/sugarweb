package com.sugarcoat.support.scheduler;

import lombok.*;

import java.io.Serializable;

/**
 * SgcSchedulerTask
 *
 * @author 许向东
 * @date 2023/10/18
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SgcSchedulerTask implements SchedulerTask, Serializable {

    private String taskName;

    private String triggerName;

    private String cron;

    private String defaultCron;

    private String executeStatus;

    private String schedulerStatus;

    private String beanName;

    private String methodName;

    private String[] paramInfos;

    private String[] params;

    private String[] defaultParams;

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
