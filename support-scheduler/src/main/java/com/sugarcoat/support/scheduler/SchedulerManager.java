package com.sugarcoat.support.scheduler;

import java.util.List;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @date 2023/10/17
 */
public interface SchedulerManager {

    void add(SchedulerTask schedulerTask);

    void update(SchedulerTask schedulerTask);

    void resume(String name);

    void pause(String name);

    void delete(String name);

    boolean exists(String name);

    void run(SchedulerTask schedulerTask);

    List<SchedulerTask> getAll();

    SchedulerTask getOne(String name);

    void start();

}
