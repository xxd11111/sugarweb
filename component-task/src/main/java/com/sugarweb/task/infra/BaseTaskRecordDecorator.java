package com.sugarweb.task.infra;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.task.domain.TaskInfo;
import com.sugarweb.task.domain.TaskTrigger;
import com.sugarweb.task.domain.TaskTriggerLog;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * taskBean的装饰者
 *
 * @author 许向东
 * @version 1.0
 */
@Slf4j
public class BaseTaskRecordDecorator implements TaskBean {

    private final TaskBean taskBean;

    private final TaskInfo taskInfo;

    private final TaskTrigger taskTrigger;

    public BaseTaskRecordDecorator(TaskBean taskBean, TaskInfo taskInfo, TaskTrigger taskTrigger) {
        this.taskBean = taskBean;
        this.taskInfo = taskInfo;
        this.taskTrigger = taskTrigger;
    }

    @Override
    public void execute() {
        LocalDateTime now = LocalDateTime.now();
        Exception exp = null;
        try {
            taskBean.execute();
        } catch (Exception e) {
            exp = e;
            log.error("任务执行失败", e);
        }
        LocalDateTime end = LocalDateTime.now();
        TaskTriggerLog taskTriggerLog = buildTriggerLog(taskInfo, taskTrigger, now, end, exp);
        Db.save(taskTriggerLog);
    }

    private TaskTriggerLog buildTriggerLog(TaskInfo taskInfo, TaskTrigger taskTrigger, LocalDateTime now, LocalDateTime end, Exception e) {
        TaskTriggerLog taskTriggerLog = new TaskTriggerLog();
        taskTriggerLog.setTaskId(taskInfo.getTaskId());
        taskTriggerLog.setTaskCode(taskInfo.getTaskCode());
        taskTriggerLog.setTaskName(taskInfo.getTaskName());
        taskTriggerLog.setBeanName(taskInfo.getBeanName());
        taskTriggerLog.setTriggerId(taskTrigger.getTriggerId());
        taskTriggerLog.setTriggerName(taskTrigger.getTriggerName());
        taskTriggerLog.setCron(taskTrigger.getCron());
        taskTriggerLog.setStartTime(now);
        taskTriggerLog.setEndTime(end);
        taskTriggerLog.setCostTime(end.getNano() - now.getNano());
        if (e == null) {
            taskTriggerLog.setExecuteResult("success");
            taskTriggerLog.setExceptionMessage("");
            taskTriggerLog.setTraceMessage("");
        } else {
            taskTriggerLog.setExecuteResult("fail");
            taskTriggerLog.setExceptionMessage(e.getMessage());
            String traceMessage = ExceptionUtil.stacktraceToString(e, 500);
            taskTriggerLog.setTraceMessage(traceMessage);
        }
        return taskTriggerLog;
    }


}
