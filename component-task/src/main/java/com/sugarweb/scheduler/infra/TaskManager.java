package com.sugarweb.scheduler.infra;

import com.sugarweb.scheduler.po.TaskInfo;
import com.sugarweb.scheduler.po.TaskTrigger;

/**
 * TaskManager
 *
 * @author 许向东
 * @version 1.0
 */
public interface TaskManager {

    void init();

    void saveTask(TaskInfo taskInfo);

    void updateTask(TaskInfo taskInfo);

    void saveTrigger(TaskTrigger taskTrigger);

    void updateTrigger(TaskTrigger taskTrigger);

    void saveTaskTrigger(TaskInfo taskInfo, TaskTrigger taskTrigger);

    void removeTask(String taskId);

    void removeTrigger(String triggerId);

    void enabledTrigger(String triggerId);

    void disabledTrigger(String triggerId);

    void runTaskOnce(String beanName);
}
