package com.sugarweb.task.infra;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.task.domain.TaskInfo;
import com.sugarweb.task.domain.TaskTrigger;
import jakarta.annotation.Resource;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;

/**
 * SchedulerManager 用于管理定时任务
 *
 * @author 许向东
 * @version 1.0
 */
public class QuartzTaskManager implements TaskManager {

    @Resource
    private Scheduler scheduler;


    @Override
    public void init() {

    }

    @Override
    public void saveTask(TaskInfo taskInfo) {
        Db.save(taskInfo);
        JobDetail jobDetail = JobBuilder
                .newJob(SchedulerJobBean.class)
                .withIdentity(taskInfo.getTaskId())
                .usingJobData("beanName", taskInfo.getBeanName())
                .build();
    }

    @Override
    public void updateTask(TaskInfo taskInfo) {

    }

    @Override
    public void removeTask(String taskId) {

    }

    @Override
    public void enabledTask(String taskId) {

    }

    @Override
    public void disabledTask(String taskId) {

    }

    @Override
    public void saveTrigger(TaskTrigger taskTrigger) {

    }

    @Override
    public void updateTrigger(TaskTrigger taskTrigger) {

    }

    @Override
    public void saveTaskTrigger(TaskInfo taskInfo, TaskTrigger taskTrigger) {

    }


    @Override
    public void removeTrigger(String triggerId) {

    }

    @Override
    public void enabledTrigger(String triggerId) {

    }

    @Override
    public void disabledTrigger(String triggerId) {

    }

    @Override
    public void runTaskOnce(String beanName) {

    }


    // public void add(TaskInfo schedulerTask) {
    //     schedulerTaskRepository.save(schedulerTask);
    //     JobDetail jobDetail = JobBuilder
    //             .newJob(SchedulerJobBean.class)
    //             .withIdentity(schedulerTask.getId())
    //             .usingJobData("id", schedulerTask.getId())
    //             .build();
    //     // 构建Cron调度器
    //     CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
    //             .cronSchedule(schedulerTask.getCron())
    //             .withMisfireHandlingInstructionDoNothing();
    //     // 任务触发器
    //     CronTrigger trigger = TriggerBuilder
    //             .newTrigger()
    //             .forJob(jobDetail)
    //             .withIdentity(schedulerTask.getId())
    //             .withSchedule(scheduleBuilder)
    //             .build();
    //     try {
    //         scheduler.scheduleJob(jobDetail, trigger);
    //         if (Flag.FALSE.getValue().equals(schedulerTask.getStatus())) {
    //             scheduler.pauseJob(JobKey.jobKey(schedulerTask.getId()));
    //         }
    //     } catch (SchedulerException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
    //
    // public void update(TaskInfo schedulerTask) {
    //     schedulerTaskRepository.save(schedulerTask);
    //     try {
    //         // 构建Cron调度器
    //         CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
    //                 .cronSchedule(schedulerTask.getCron())
    //                 .withMisfireHandlingInstructionDoNothing();
    //         // 任务触发器
    //         CronTrigger trigger = TriggerBuilder
    //                 .newTrigger()
    //                 .forJob(schedulerTask.getId())
    //                 .withIdentity(schedulerTask.getId())
    //                 .withSchedule(scheduleBuilder)
    //                 .build();
    //         scheduler.rescheduleJob(TriggerKey.triggerKey(schedulerTask.getId()), trigger);
    //         if (Flag.FALSE.getValue().equals(schedulerTask.getStatus())) {
    //             scheduler.pauseJob(JobKey.jobKey(schedulerTask.getId()));
    //         } else {
    //             scheduler.resumeJob(JobKey.jobKey(schedulerTask.getId()));
    //         }
    //     } catch (SchedulerException e) {
    //         throw new RuntimeException("updateJob Fail", e);
    //     }
    // }
    //
    // public void resume(String id) {
    //     TaskInfo schedulerTask = Optional.ofNullable(schedulerTaskRepository.selectById(id))
    //             .orElseThrow(() -> new ValidateException("定时任务不存在"));
    //     schedulerTask.setStatus(Flag.TRUE.getValue());
    //     schedulerTaskRepository.save(schedulerTask);
    //     try {
    //         scheduler.resumeJob(JobKey.jobKey(id));
    //     } catch (SchedulerException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
    //
    // public void pause(String id) {
    //     TaskInfo schedulerTask = Optional.ofNullable(schedulerTaskRepository.selectById(id))
    //             .orElseThrow(() -> new ValidateException("定时任务不存在"));
    //     schedulerTask.setStatus(Flag.FALSE.getValue());
    //     schedulerTaskRepository.save(schedulerTask);
    //     try {
    //         scheduler.pauseJob(JobKey.jobKey(id));
    //     } catch (SchedulerException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
    //
    // public void delete(String id) {
    //     TaskInfo schedulerTask = Optional.ofNullable(schedulerTaskRepository.selectById(id))
    //             .orElseThrow(() -> new ValidateException("定时任务不存在"));
    //     schedulerTask.setStatus(Flag.FALSE.getValue());
    //     schedulerTaskRepository.deleteById(schedulerTask);
    //     try {
    //         scheduler.deleteJob(JobKey.jobKey(id));
    //     } catch (SchedulerException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
    //
    // public boolean exists(String id) {
    //     return schedulerTaskRepository.exists(new LambdaQueryWrapper<TaskInfo>().eq(TaskInfo::getId, id));
    // }
    //
    // public void run(String id) {
    //     if (null == schedulerTaskRepository.selectById(id)) {
    //         throw new ValidateException("定时任务不存在");
    //     }
    //     try {
    //         scheduler.triggerJob(JobKey.jobKey(id));
    //     } catch (SchedulerException e) {
    //         throw new RuntimeException("run Fail", e);
    //     }
    // }
    //
    // public List<TaskInfo> getAll() {
    //     return schedulerTaskRepository.selectList();
    // }
    //
    // public TaskInfo getOne(String id) {
    //     return Optional.ofNullable(schedulerTaskRepository.selectById(id))
    //             .orElseThrow(() -> new ValidateException("定时任务不存在"));
    // }
    //
    // public void reset() {
    //     try {
    //         scheduler.clear();
    //         List<TaskInfo> all = schedulerTaskRepository.selectList();
    //         for (TaskInfo schedulerTask : all) {
    //             this.add(schedulerTask);
    //         }
    //         if (scheduler.isInStandbyMode()) {
    //             scheduler.start();
    //         }
    //     } catch (SchedulerException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
}
