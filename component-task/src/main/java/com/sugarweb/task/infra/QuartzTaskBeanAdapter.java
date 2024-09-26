package com.sugarweb.task.infra;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.task.domain.po.TaskInfo;
import com.sugarweb.task.domain.po.TaskTrigger;
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
public class QuartzTaskBeanAdapter extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        try {
            String triggerId = context.getTrigger().getKey().getName();
            String taskId = context.getJobDetail().getKey().getName();
            TaskInfo taskInfo = Db.getById(taskId, TaskInfo.class);
            TaskTrigger taskTrigger = Db.getById(triggerId, TaskTrigger.class);
            log.info("定时任务执行开始,taskName:{},triggerName:{}", taskInfo.getTaskName(), taskTrigger.getTriggerName());
            TaskBean taskBean = TaskBeanFactory.getTaskBean(taskInfo.getBeanName());
            TaskRecordDecorator taskRecordDecorator = new TaskRecordDecorator(taskBean, taskInfo, taskTrigger);
            taskRecordDecorator.run();
            log.info("定时任务执行结束,taskName:{},triggerName:{}", taskInfo.getTaskName(), taskTrigger.getTriggerName());
        } catch (Throwable ex) {
            log.error("定时任务执行异常", ex);
        }
    }

}
