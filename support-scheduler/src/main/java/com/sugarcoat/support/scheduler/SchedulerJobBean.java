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

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        SchedulerTask schedulerTask = (SchedulerTask) mergedJobDataMap.get("1");
        String taskStatus = schedulerTask.getTaskStatus();
        if ("0".equals(taskStatus)){
            log.debug("定时任务未启用,不执行:{}", schedulerTask.getTaskName());
            return;
        }
        try {
            Object target = BeanUtil.getBean(schedulerTask.getBeanName());
            Method method = target.getClass().getDeclaredMethod(schedulerTask.getMethodName(), String.class);
            method.invoke(target, schedulerTask.getParams());
        } catch (Throwable ex) {
            log.error("定时任务执行异常", ex);
            //todo 重试处理
            String retryTimes = schedulerTask.getRetryTimes();
            String retryStrategy = schedulerTask.getRetryStrategy();
        } finally {

        }

    }

}
