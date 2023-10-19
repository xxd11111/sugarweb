package com.sugarcoat.support.scheduler;

import com.sugarcoat.protocol.common.SgcRunner;
import org.springframework.boot.ApplicationArguments;

/**
 * 定时任务初始化
 *
 * @author 许向东
 * @date 2023/10/18
 */
public class SchedulerRunner implements SgcRunner {

    private final TaskBeanRegistry taskBeanRegistry;

    public SchedulerRunner(TaskBeanRegistry taskBeanRegistry) {
        this.taskBeanRegistry = taskBeanRegistry;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        taskBeanRegistry.register();
    }

}
