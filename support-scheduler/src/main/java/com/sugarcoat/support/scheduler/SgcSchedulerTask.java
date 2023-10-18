package com.sugarcoat.support.scheduler;

import lombok.Data;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/10/18
 */
@Data
public class SgcSchedulerTask implements SchedulerTask {

    private String schedulerName;

    private String schedulerGroup;

    private String cron;

    private Object[] executeParams;

    private TaskBean taskBean;

    private String lastExecuteTime;

    private String nextExecuteTime;

    private String retryStrategy;

    private String retryTimes;

    private String delayStrategy;

    private String delayTime;

    private String executeStatus;

    private String schedulerStatus;

    @Override
    public String getTaskName() {
        return null;
    }

    @Override
    public String getTaskGroup() {
        return null;
    }

    @Override
    public String getBeanName() {
        return null;
    }

    @Override
    public String getMethodName() {
        return null;
    }

    @Override
    public Object[] getParams() {
        return new Object[0];
    }

    @Override
    public String[] getParamsInfo() {
        return new String[0];
    }

    @Override
    public String getTaskStatus() {
        return null;
    }
}
