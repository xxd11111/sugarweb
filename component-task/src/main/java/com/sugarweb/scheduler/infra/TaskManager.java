package com.sugarweb.scheduler.infra;

import com.sugarweb.scheduler.po.TaskInfo;
import com.sugarweb.scheduler.po.TaskTrigger;

import java.util.List;

/**
 * TaskManager
 *
 * @author 许向东
 * @version 1.0
 */
public interface TaskManager {

    void initLoad();

    void saveTask(TaskInfo taskInfo);

    void updateTask(TaskInfo taskInfo);

    void saveTrigger(TaskTrigger taskTrigger);

    void updateTrigger(TaskTrigger taskTrigger);

    void saveTaskTrigger(TaskInfo taskInfo, TaskTrigger taskTrigger);

    void removeTask(String taskId);

    void removeTrigger(String triggerId);

    boolean existsBean(String beanName);

    TaskBean getTaskBean(String beanName);

    List<String> getTaskBeanNames();

    void startTrigger(String triggerId);

    void stopTrigger(String triggerId);

    void runTaskOnce(String beanName);
}
