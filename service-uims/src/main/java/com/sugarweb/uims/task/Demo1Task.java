package com.sugarweb.uims.task;

import com.sugarweb.task.infra.auto.InnerTask;
import com.sugarweb.task.infra.auto.InnerTaskTrigger;
import com.sugarweb.task.infra.TaskBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Demo1Task
 *
 * @author 许向东
 * @version 1.0
 */
@Slf4j
@Component
@InnerTask(taskCode = "Demo1Task", taskName = "测试任务1")
@InnerTaskTrigger(triggerCode = "1001", triggerName = "Demo1Task-1001", cron = "0/5 * * * * ?")
public class Demo1Task implements TaskBean {
    @Override
    public void run() {
        log.info("executeTask:demo1");
    }

}
