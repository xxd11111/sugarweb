package com.sugarweb.task.infra;

/**
 * BaseTask
 *
 * @author 许向东
 * @version 1.0
 */
public class BaseTask implements TaskBean{

    private final String beanName;
    private final TaskBean taskBean;

    public BaseTask(String beanName) {
        this.beanName = beanName;
        this.taskBean = TaskBeanAdapter.getTaskBean(beanName);
    }

    public void execute() {
        if (taskBean == null) {
            throw new IllegalArgumentException("未找到指定TaskBean,定时任务执行失败！beanName:" + beanName);
        }
        taskBean.execute();
    }

}
