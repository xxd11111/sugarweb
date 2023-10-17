package com.sugarcoat.support.scheduler;

import com.sugarcoat.protocol.BeanUtil;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
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
public class SchedulerBean extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        SchedulerInfo schedulerInfo = (SchedulerInfo) mergedJobDataMap.get("1");
        try {
            Object target = BeanUtil.getBean(schedulerInfo.getBeanName());
            Method method = target.getClass().getDeclaredMethod(schedulerInfo.getMethodName(), String.class);
            method.invoke(target, schedulerInfo.getParams());
        } catch (Throwable ex) {
            log.error("定时任务执行异常", ex);
            //todo 重试处理
            String retryTimes = schedulerInfo.getRetryTimes();
            String retryStrategy = schedulerInfo.getRetryStrategy();
        } finally {

        }

    }

}
