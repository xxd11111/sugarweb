package com.sugarweb.scheduler.infra;

import com.sugarweb.scheduler.domain.SchedulerTask;

import java.util.List;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @version 1.0
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
