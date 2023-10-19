package com.sugarcoat.support.scheduler;


/**
 * TaskBeanFactory
 *
 * @author 许向东
 * @date 2023/10/19
 */
public interface TaskBeanFactory {

    Object getBean(String taskName);

}
