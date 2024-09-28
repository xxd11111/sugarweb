package com.sugarweb.task.infra;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.task.domain.po.TaskInfo;
import com.sugarweb.task.domain.po.TaskTrigger;
import com.sugarweb.task.domain.po.TaskTriggerLog;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * taskBean的装饰者
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class TaskRecordDecorator implements TaskBean {

    private final TaskBean taskBean;

    private final TaskInfo taskInfo;

    private final TaskTrigger taskTrigger;

    public TaskRecordDecorator(TaskBean taskBean, TaskInfo taskInfo, TaskTrigger taskTrigger) {
        this.taskBean = taskBean;
        this.taskInfo = taskInfo;
        this.taskTrigger = taskTrigger;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        try {
            taskBean.run();
            Thread.ofVirtual().start(() -> saveTriggerLog(taskInfo, taskTrigger, now, LocalDateTime.now(), null));
        } catch (Exception ex) {
            Thread.ofVirtual().start(() -> saveTriggerLog(taskInfo, taskTrigger, now, LocalDateTime.now(), ex));
            throw ex;
        }
    }

    private void saveTriggerLog(TaskInfo taskInfo, TaskTrigger taskTrigger, LocalDateTime start, LocalDateTime end, Exception ex) {
        Db.save(buildTriggerLog(taskInfo, taskTrigger, start, end, ex));
    }

    private TaskTriggerLog buildTriggerLog(TaskInfo taskInfo, TaskTrigger taskTrigger, LocalDateTime now, LocalDateTime end, Exception ex) {
        TaskTriggerLog taskTriggerLog = new TaskTriggerLog();
        if (taskInfo != null) {
            taskTriggerLog.setTaskId(taskInfo.getTaskId());
            taskTriggerLog.setTaskCode(taskInfo.getTaskCode());
            taskTriggerLog.setTaskName(taskInfo.getTaskName());
            taskTriggerLog.setBeanName(taskInfo.getBeanName());
        }
        if (taskTrigger != null) {
            taskTriggerLog.setTriggerId(taskTrigger.getTriggerId());
            taskTriggerLog.setTriggerName(taskTrigger.getTriggerName());
            taskTriggerLog.setCron(taskTrigger.getCron());
        }

        taskTriggerLog.setStartTime(now);
        taskTriggerLog.setEndTime(end);
        taskTriggerLog.setCostTime(end.getNano() - now.getNano());

        if (ex == null) {
            taskTriggerLog.setExecuteResult("success");
            taskTriggerLog.setExceptionMessage("");
            taskTriggerLog.setTraceMessage("");
        } else {
            taskTriggerLog.setExecuteResult("fail");
            taskTriggerLog.setExceptionMessage(ex.getMessage());
            String traceMessage = ExceptionUtil.stacktraceToString(ex, 500);
            taskTriggerLog.setTraceMessage(traceMessage);
        }
        return taskTriggerLog;
    }


}
