package com.sugarcoat.support.scheduler.domain;

import com.sugarcoat.protocol.scheduler.SchedulerTask;
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

    private String customCron;

    private String defaultCron;

    private String executeStatus;

    private String beanName;

    private String methodName;

    private Integer paramsLength;

    private String customParams;

    private String defaultParams;

    @Override
    public String getCron() {
        return customCron;
    }

    @Override
    public String getParams() {
        return customParams;
    }

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
