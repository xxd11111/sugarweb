package com.sugarcoat.support.scheduler.domain;

import com.sugarcoat.protocol.BeanUtil;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.protocol.scheduler.InnerTaskBean;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.annotation.Annotation;
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
        String id = context.getJobDetail().getKey().getName();
        SgcSchedulerTaskRepository sgcSchedulerTaskRepository = BeanUtil.getBean(SgcSchedulerTaskRepository.class);
        SgcSchedulerTask schedulerTask = sgcSchedulerTaskRepository.findById(id)
                .orElseThrow(() -> new FrameworkException("定时任务异常：未根据id找到指定任务，id:{}", id));
        log.info("定时任务执行开始：{}", schedulerTask.getTaskName());
        try {
            Object taskBean = BeanUtil.getBean(schedulerTask.getBeanName());
            Annotation annotation = taskBean.getClass().getAnnotation(InnerTaskBean.class);
            if (annotation == null) {
                throw new ValidateException("非定时任务bean禁止启用，{}", schedulerTask);
            }
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
