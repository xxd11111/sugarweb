package com.sugarweb.task.infra;

import com.sugarweb.task.infra.auto.TaskAutoRegistry;
import jakarta.annotation.Resource;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * TaskBeanAdapter
 *
 * @author 许向东
 * @version 1.0
 */
public class SpringbootTaskAdapter implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Resource
    private TaskAutoRegistry taskAutoRegistry;
    @Resource
    private TaskManager taskManager;
    @Resource
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化bean容器
        Map<String, TaskBean> beansOfType = applicationContext.getBeansOfType(TaskBean.class);
        TaskBeanFactory.load(beansOfType);

        //导入默认任务
        taskAutoRegistry.run();

        //启动定时任务
        scheduler.start();
        //quartz重新加载任务
        taskManager.reload();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
