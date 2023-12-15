package com.sugarcoat.support.scheduler.domain;

import com.sugarcoat.protocol.scheduler.SchedulerManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * 定时任务初始化
 *
 * @author 许向东
 * @date 2023/10/18
 */
public class SchedulerRunner implements ApplicationRunner {

    private final SchedulerManager schedulerManager;
    private final SgcSchedulerTaskRepository sgcSchedulerTaskRepository;

    public SchedulerRunner(SchedulerManager schedulerManager, SgcSchedulerTaskRepository sgcSchedulerTaskRepository) {
        this.schedulerManager = schedulerManager;
        this.sgcSchedulerTaskRepository = sgcSchedulerTaskRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        schedulerManager.clear();
        Iterable<SgcSchedulerTask> all = sgcSchedulerTaskRepository.findAll();
        for (SgcSchedulerTask sgcSchedulerTask : all) {
            schedulerManager.add(sgcSchedulerTask);
        }
        schedulerManager.start();
    }

}
