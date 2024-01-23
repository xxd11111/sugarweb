package com.sugarcoat.support.scheduler.api;

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

    void resume(String id);

    void pause(String id);

    void delete(String id);

    boolean exists(String id);

    void run(String id);

    List<SchedulerTask> getAll();

    SchedulerTask getOne(String id);

    void reset();

}
