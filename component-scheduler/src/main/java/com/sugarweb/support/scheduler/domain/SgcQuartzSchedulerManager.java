package com.sugarweb.support.scheduler.domain;

import com.sugarweb.exception.ValidateException;
import com.sugarweb.support.scheduler.api.SchedulerManager;
import com.sugarweb.support.scheduler.api.SchedulerTask;
import com.sugarweb.orm.BooleanEnum;
import jakarta.annotation.Resource;
import org.quartz.*;

import java.util.*;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @version 1.0
 */
public class SgcQuartzSchedulerManager implements SchedulerManager {

    @Resource
    private Scheduler scheduler;
    @Resource
    private BaseSchedulerTaskRepository sgcSchedulerTaskRepository;

    public void add(SchedulerTask schedulerTask) {
        SgcSchedulerTask sgcSchedulerTask = (SgcSchedulerTask) schedulerTask;
        sgcSchedulerTaskRepository.save(sgcSchedulerTask);
        JobDetail jobDetail = JobBuilder
                .newJob(SchedulerJobBean.class)
                .withIdentity(sgcSchedulerTask.getId())
                .usingJobData("id", sgcSchedulerTask.getId())
                .build();
        // 构建Cron调度器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(sgcSchedulerTask.getCron())
                .withMisfireHandlingInstructionDoNothing();
        // 任务触发器
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(sgcSchedulerTask.getId())
                .withSchedule(scheduleBuilder)
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (BooleanEnum.FALSE.getValue().equals(sgcSchedulerTask.getStatus())) {
                scheduler.pauseJob(JobKey.jobKey(sgcSchedulerTask.getId()));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(SchedulerTask schedulerTask) {
        SgcSchedulerTask sgcSchedulerTask = (SgcSchedulerTask) schedulerTask;
        sgcSchedulerTaskRepository.save(sgcSchedulerTask);
        try {
            // 构建Cron调度器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(sgcSchedulerTask.getCron())
                    .withMisfireHandlingInstructionDoNothing();
            // 任务触发器
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .forJob(sgcSchedulerTask.getId())
                    .withIdentity(sgcSchedulerTask.getId())
                    .withSchedule(scheduleBuilder)
                    .build();
            scheduler.rescheduleJob(TriggerKey.triggerKey(sgcSchedulerTask.getId()), trigger);
            if (BooleanEnum.FALSE.getValue().equals(sgcSchedulerTask.getStatus())) {
                scheduler.pauseJob(JobKey.jobKey(sgcSchedulerTask.getId()));
            } else {
                scheduler.resumeJob(JobKey.jobKey(sgcSchedulerTask.getId()));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("updateJob Fail", e);
        }
    }

    public void resume(String id) {
        SgcSchedulerTask sgcSchedulerTask = sgcSchedulerTaskRepository.findById(id)
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        sgcSchedulerTask.setStatus(BooleanEnum.TRUE.getValue());
        sgcSchedulerTaskRepository.save(sgcSchedulerTask);
        try {
            scheduler.resumeJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void pause(String id) {
        SgcSchedulerTask sgcSchedulerTask = sgcSchedulerTaskRepository.findById(id)
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        sgcSchedulerTask.setStatus(BooleanEnum.FALSE.getValue());
        sgcSchedulerTaskRepository.save(sgcSchedulerTask);
        try {
            scheduler.pauseJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        SgcSchedulerTask sgcSchedulerTask = sgcSchedulerTaskRepository.findById(id)
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        sgcSchedulerTask.setStatus(BooleanEnum.FALSE.getValue());
        sgcSchedulerTaskRepository.delete(sgcSchedulerTask);
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
        Iterable<SgcSchedulerTask> all = sgcSchedulerTaskRepository.findAll();
        List<SchedulerTask> schedulerTasks = new ArrayList<>();
        for (SgcSchedulerTask sgcSchedulerTask : all) {
            schedulerTasks.add(sgcSchedulerTask);
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
            Iterable<SgcSchedulerTask> all = sgcSchedulerTaskRepository.findAll();
            for (SgcSchedulerTask sgcSchedulerTask : all) {
                this.add(sgcSchedulerTask);
            }
            if (scheduler.isInStandbyMode()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
