package com.sugarcoat.support.scheduler;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/10/18
 */
public class SchedulerService {

    private SgcSchedulerManager schedulerManager;

    public void add(SchedulerTaskDto dto){

    }

    public void delete(SchedulerTaskDto dto){

    }

    public void remove(SchedulerTaskDto dto){

    }

    public void update(SchedulerTaskDto dto){

    }

    public void updateStatus(SchedulerTaskDto dto){

    }

    public void pause(SchedulerTaskDto dto){
        // schedulerManager.pause();
    }

    public void resume(SchedulerTaskDto dto){
        // schedulerManager.resume();
    }

    public void run(SchedulerTaskDto dto){
        // schedulerManager.run();
    }

    public void interrupt(String name){
        schedulerManager.interrupt(name, null);
    }


}
