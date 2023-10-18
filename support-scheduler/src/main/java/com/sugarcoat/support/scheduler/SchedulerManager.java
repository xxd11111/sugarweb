package com.sugarcoat.support.scheduler;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @date 2023/10/17
 */
public interface SchedulerManager {

    public void add(SchedulerTask schedulerTask);

    public void update(SchedulerTask schedulerTask);

    public void resume(String name, String group);

    public void pause(String name, String group);

    public void delete(String name, String group);

    public void interrupt(String name, String group);

    public boolean exists(String name, String group);

    public void run(SchedulerTask schedulerTask);

}
