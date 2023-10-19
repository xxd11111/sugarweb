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

    @InnerTaskMethod(cron = "0/5 * * * * ? ")
    public void executeTask() {
        log.info("executeTask:demo1");
    }

}
