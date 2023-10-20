package com.sugarcoat.uims.task;

import com.sugarcoat.support.scheduler.InnerTaskBean;
import com.sugarcoat.support.scheduler.InnerTaskMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Demo1Task
 *
 * @author 许向东
 * @date 2023/10/19
 */
@Slf4j
@Component
@InnerTaskBean
public class Demo1Task {

    @InnerTaskMethod(cron = "0/20 * * * * ? ", params = "123")
    public void executeTask(String params) throws InterruptedException {
        Thread.sleep(10000);
        log.info("executeTask:demo1," + params);
    }

}
