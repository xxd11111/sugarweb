package com.sugarweb.task.infra;

import com.sugarweb.task.infra.TaskBean;
import com.sugarweb.task.infra.TaskBeanAdapter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 任务bean
 *
 * @author xxd
 * @version 1.0
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Slf4j
public class SchedulerJobBean extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        String id = context.getJobDetail().getKey().getName();
        TaskBean taskBean = TaskBeanAdapter.getTaskBean(id);
        log.info("定时任务执行开始：{}", id);
        try {
            taskBean.execute();
        } catch (Throwable ex) {
            log.error("定时任务执行异常", ex);
        }
        log.info("定时任务执行结束：{}", id);
    }

}
