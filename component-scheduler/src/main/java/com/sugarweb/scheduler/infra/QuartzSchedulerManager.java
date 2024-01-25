package com.sugarweb.scheduler.infra;

import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.scheduler.domain.SchedulerTask;
import com.sugarweb.scheduler.domain.SchedulerTaskRepository;
import com.sugarweb.framework.orm.BooleanEnum;
import jakarta.annotation.Resource;
import org.quartz.*;

import java.util.*;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @version 1.0
 */
public class QuartzSchedulerManager implements SchedulerManager {

    @Resource
    private Scheduler scheduler;
    @Resource
    private SchedulerTaskRepository sgcSchedulerTaskRepository;

    public void add(SchedulerTask schedulerTask) {
        sgcSchedulerTaskRepository.save(schedulerTask);
        JobDetail jobDetail = JobBuilder
                .newJob(SchedulerJobBean.class)
                .withIdentity(schedulerTask.getId())
                .usingJobData("id", schedulerTask.getId())
                .build();
        // 构建Cron调度器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(schedulerTask.getCron())
                .withMisfireHandlingInstructionDoNothing();
        // 任务触发器
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(schedulerTask.getId())
                .withSchedule(scheduleBuilder)
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (BooleanEnum.FALSE.getValue().equals(schedulerTask.getStatus())) {
                scheduler.pauseJob(JobKey.jobKey(schedulerTask.getId()));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(SchedulerTask schedulerTask) {
        sgcSchedulerTaskRepository.save(schedulerTask);
        try {
            // 构建Cron调度器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(schedulerTask.getCron())
                    .withMisfireHandlingInstructionDoNothing();
            // 任务触发器
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .forJob(schedulerTask.getId())
                    .withIdentity(schedulerTask.getId())
                    .withSchedule(scheduleBuilder)
                    .build();
            scheduler.rescheduleJob(TriggerKey.triggerKey(schedulerTask.getId()), trigger);
            if (BooleanEnum.FALSE.getValue().equals(schedulerTask.getStatus())) {
                scheduler.pauseJob(JobKey.jobKey(schedulerTask.getId()));
            } else {
                scheduler.resumeJob(JobKey.jobKey(schedulerTask.getId()));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("updateJob Fail", e);
        }
    }

    public void resume(String id) {
        SchedulerTask schedulerTask = sgcSchedulerTaskRepository.findById(id)
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        schedulerTask.setStatus(BooleanEnum.TRUE.getValue());
        sgcSchedulerTaskRepository.save(schedulerTask);
        try {
            scheduler.resumeJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void pause(String id) {
        SchedulerTask schedulerTask = sgcSchedulerTaskRepository.findById(id)
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        schedulerTask.setStatus(BooleanEnum.FALSE.getValue());
        sgcSchedulerTaskRepository.save(schedulerTask);
        try {
            scheduler.pauseJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        SchedulerTask schedulerTask = sgcSchedulerTaskRepository.findById(id)
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        schedulerTask.setStatus(BooleanEnum.FALSE.getValue());
        sgcSchedulerTaskRepository.delete(schedulerTask);
        try {
            scheduler.deleteJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists(String id) {
        return sgcSchedulerTaskRepository.existsById(id);
    }

    public void run(String id) {
        if (!sgcSchedulerTaskRepository.existsById(id)) {
            throw new ValidateException("定时任务不存在");
        }
        try {
            scheduler.triggerJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException("run Fail", e);
        }
    }

    @Override
    public List<SchedulerTask> getAll() {
        Iterable<SchedulerTask> all = sgcSchedulerTaskRepository.findAll();
        List<SchedulerTask> schedulerTasks = new ArrayList<>();
        for (SchedulerTask schedulerTask : all) {
            schedulerTasks.add(schedulerTask);
        }
        return schedulerTasks;
    }

    @Override
    public SchedulerTask getOne(String id) {
        return sgcSchedulerTaskRepository.findById(id)
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
    }

    public void reset() {
        try {
            scheduler.clear();
            Iterable<SchedulerTask> all = sgcSchedulerTaskRepository.findAll();
            for (SchedulerTask schedulerTask : all) {
                this.add(schedulerTask);
            }
            if (scheduler.isInStandbyMode()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
