package com.sugarweb.scheduler.auto;

import com.sugarweb.scheduler.infra.SchedulerManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * 定时任务初始化
 *
 * @author 许向东
 * @version 1.0
 */
public class SchedulerRunner implements ApplicationRunner {

    private final SchedulerManager schedulerManager;

    public SchedulerRunner(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        schedulerManager.reset();
    }

}
