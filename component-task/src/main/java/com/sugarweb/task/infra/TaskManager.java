package com.sugarweb.task.infra;

import com.sugarweb.task.domain.TaskInfo;
import com.sugarweb.task.domain.TaskTrigger;

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

    void removeTask(String taskId);

    void enabledTask(String taskId);

    void disabledTask(String taskId);

    void saveTrigger(TaskTrigger taskTrigger);

    void updateTrigger(TaskTrigger taskTrigger);

    void saveTaskTrigger(TaskInfo taskInfo, TaskTrigger taskTrigger);

    void removeTrigger(String triggerId);

    void enabledTrigger(String triggerId);

    void disabledTrigger(String triggerId);

    void runTaskOnce(String beanName);
}
