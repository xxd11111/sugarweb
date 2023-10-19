package com.sugarcoat.support.scheduler;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
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

    private String executeStatus;

    private String schedulerStatus;

    private String beanName;

    private String methodName;

    private String[] params;

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
