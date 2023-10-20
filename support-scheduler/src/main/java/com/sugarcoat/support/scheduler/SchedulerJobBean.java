package com.sugarcoat.support.scheduler;

import com.sugarcoat.protocol.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * 任务bean
 *
 * @author xxd
 * @date 2023/10/17
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Slf4j
public class SchedulerJobBean extends QuartzJobBean {

    private final TaskBeanFactory taskBeanFactory = BeanUtil.getBean(TaskBeanFactory.class);

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        SgcSchedulerTask schedulerTask = (SgcSchedulerTask) mergedJobDataMap.get("1");
        log.info("定时任务执行开始：{}", schedulerTask.getTaskName());
        try {
            Object taskBean = taskBeanFactory.getBean(schedulerTask.getBeanName());
            if (schedulerTask.getParamsLength() == 1) {
                Method method = taskBean.getClass().getDeclaredMethod(schedulerTask.getMethodName(), String.class);
                String params = schedulerTask.getParams();
                method.invoke(taskBean, params);
            } else {
                Method method = taskBean.getClass().getDeclaredMethod(schedulerTask.getMethodName());
                method.invoke(taskBean);
            }
        } catch (Throwable ex) {
            log.error("定时任务执行异常", ex);
        }
        log.info("定时任务执行结束：{}", schedulerTask.getTaskName());
    }

}
