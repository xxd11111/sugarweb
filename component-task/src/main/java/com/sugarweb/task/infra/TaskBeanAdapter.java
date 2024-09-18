package com.sugarweb.task.infra;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.Map;

/**
 * TaskBeanAdapter
 *
 * @author 许向东
 * @version 1.0
 */
public class TaskBeanAdapter {

    private static Map<String, TaskBean> taskBeanMap;

    protected static void load(Map<String, TaskBean> taskBeanMap) {
        TaskBeanAdapter.taskBeanMap = taskBeanMap;
    }

    public static boolean existsBean(String beanName) {
        return taskBeanMap.containsKey(beanName);
    }

    public static TaskBean getTaskBean(String beanName) {
        return taskBeanMap.get(beanName);
    }

    public static List<String> getTaskBeanNames() {
        return CollUtil.newArrayList(taskBeanMap.keySet());
    }

}
