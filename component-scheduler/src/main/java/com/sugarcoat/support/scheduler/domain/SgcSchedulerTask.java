package com.sugarcoat.support.scheduler.domain;

import com.sugarcoat.support.scheduler.api.SchedulerTask;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

/**
 * SgcSchedulerTask
 *
 * @author 许向东
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Entity
public class SgcSchedulerTask implements SchedulerTask {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 40)
    private String id;

    private String taskName;

    private String beanName;

    private String methodName;

    private String cron;

    private String defaultCron;

    private String status;

    private Integer paramsLength;

    private String params;

    private String defaultParams;

    private String taskData;

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
