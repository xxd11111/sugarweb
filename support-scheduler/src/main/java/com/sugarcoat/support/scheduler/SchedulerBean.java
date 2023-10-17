package com.sugarcoat.support.scheduler;

import com.sugarcoat.protocol.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * 任务demo
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
        SchedulerInfo schedulerInfo = (SchedulerInfo)mergedJobDataMap.get("1");
        try {
            Object target = BeanUtil.getBean(schedulerInfo.getBeanName());
            Method method = target.getClass().getDeclaredMethod(schedulerInfo.getMethodName(), String.class);
            method.invoke(target, schedulerInfo.getParams());
        } catch (Exception e){
        } finally {

        }

    }

}
