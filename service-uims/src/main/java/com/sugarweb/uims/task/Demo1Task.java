package com.sugarweb.uims.task;

import com.sugarweb.scheduler.auto.RegistryTaskTrigger;
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
// @InnerTaskBean
public class Demo1Task {

    @RegistryTaskTrigger(id = "1001", cron = "0/20 * * * * ? ", params = "123")
    public void executeTask(String params) throws InterruptedException {
        Thread.sleep(10000);
        log.info("executeTask:demo1," + params);
    }

}
