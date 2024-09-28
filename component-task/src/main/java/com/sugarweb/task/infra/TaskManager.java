package com.sugarweb.task.infra;

import com.sugarweb.task.domain.po.TaskInfo;
import com.sugarweb.task.domain.po.TaskTrigger;

/**
 * TaskManager
 *
 * @author xxd
 * @version 1.0
 */
public interface TaskManager {

    void reload();

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
