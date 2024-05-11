package com.sugarweb.scheduler.infra;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.scheduler.domain.SchedulerTask;
import com.sugarweb.scheduler.domain.SchedulerTaskRepository;
import com.sugarweb.framework.common.Flag;
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
    private SchedulerTaskRepository schedulerTaskRepository;

    @Override
    public void add(SchedulerTask schedulerTask) {
        schedulerTaskRepository.save(schedulerTask);
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
            if (Flag.FALSE.getValue().equals(schedulerTask.getStatus())) {
                scheduler.pauseJob(JobKey.jobKey(schedulerTask.getId()));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SchedulerTask schedulerTask) {
        schedulerTaskRepository.save(schedulerTask);
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
            if (Flag.FALSE.getValue().equals(schedulerTask.getStatus())) {
                scheduler.pauseJob(JobKey.jobKey(schedulerTask.getId()));
            } else {
                scheduler.resumeJob(JobKey.jobKey(schedulerTask.getId()));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("updateJob Fail", e);
        }
    }

    @Override
    public void resume(String id) {
        SchedulerTask schedulerTask = Optional.ofNullable(schedulerTaskRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        schedulerTask.setStatus(Flag.TRUE.getValue());
        schedulerTaskRepository.save(schedulerTask);
        try {
            scheduler.resumeJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pause(String id) {
        SchedulerTask schedulerTask = Optional.ofNullable(schedulerTaskRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        schedulerTask.setStatus(Flag.FALSE.getValue());
        schedulerTaskRepository.save(schedulerTask);
        try {
            scheduler.pauseJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) {
        SchedulerTask schedulerTask = Optional.ofNullable(schedulerTaskRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
        schedulerTask.setStatus(Flag.FALSE.getValue());
        schedulerTaskRepository.deleteById(schedulerTask);
        try {
            scheduler.deleteJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(String id) {
        return schedulerTaskRepository.exists(new LambdaQueryWrapper<SchedulerTask>().eq(SchedulerTask::getId, id));
    }

    @Override
    public void run(String id) {
        if (null == schedulerTaskRepository.selectById(id)) {
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
        return schedulerTaskRepository.selectList();
    }

    @Override
    public SchedulerTask getOne(String id) {
        return Optional.ofNullable(schedulerTaskRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("定时任务不存在"));
    }

    @Override
    public void reset() {
        try {
            scheduler.clear();
            List<SchedulerTask> all = schedulerTaskRepository.selectList();
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
