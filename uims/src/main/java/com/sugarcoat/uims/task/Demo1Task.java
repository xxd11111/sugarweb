package com.sugarcoat.uims.task;

import com.sugarcoat.support.scheduler.TaskBean;
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
public class Demo1Task implements TaskBean {

    @Override
    public void executeTask() {
        log.info("executeTask:demo1");
    }

}
