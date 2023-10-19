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

    void updateBean(SchedulerTask schedulerTask);

    void enable(String name);

    void disable(String name);

    void resume(String name);

    void pause(String name);

    void delete(String name);

    void interrupt(String name);

    boolean exists(String name);

    void run(String name);

    void updateStatus(String name, String status);

    List<SchedulerTask> getAll();

    SchedulerTask getOne(String name);

}
