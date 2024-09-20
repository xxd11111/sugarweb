package com.sugarweb.task.controller;

import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.task.application.TaskDto;
import com.sugarweb.task.application.TaskQuery;
import com.sugarweb.task.application.TaskService;
import com.sugarweb.task.application.TaskTriggerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务控制器
 *
 * @author 许向东
 * @version 1.0
 */
@RestController
@RequestMapping("/task")
@Tag(name = "任务管理")
public class TaskController {

    @Resource
    public TaskService taskService;

    @GetMapping("page")
    @Operation(operationId = "task:page", summary = "任务分页查询")
    public R page(PageQuery pageQuery, TaskQuery queryDto) {
        return R.data(taskService.page(pageQuery, queryDto));
    }

    @PostMapping("saveTask")
    @Operation(operationId = "task:saveTask", summary = "任务新增")
    public R saveTask(@RequestBody TaskDto dto) {
        taskService.saveTask(dto);
        return R.ok();
    }

    @PostMapping("updateTask")
    @Operation(operationId = "task:updateTask", summary = "任务更新")
    public R updateTask(@RequestBody TaskDto dto) {
        taskService.updateTask(dto);
        return R.ok();
    }

    @PostMapping("enabledTask")
    @Operation(operationId = "task:enabledTask", summary = "任务启用")
    public R enabledTask(@RequestParam String taskId) {
        taskService.enabledTask(taskId);
        return R.ok();
    }

    @PostMapping("disabledTask")
    @Operation(operationId = "task:disabledTask", summary = "任务停用")
    public R disabledTask(@RequestParam String taskId) {
        taskService.disabledTask(taskId);
        return R.ok();
    }

    @PostMapping("removeTask")
    @Operation(operationId = "task:removeTask", summary = "任务删除")
    public R removeTask(@RequestParam String taskId) {
        taskService.removeTask(taskId);
        return R.ok();
    }

    @PostMapping("saveTrigger")
    @Operation(operationId = "task:saveTrigger", summary = "触发器新增")
    public R saveTrigger(@RequestBody TaskTriggerDto dto) {
        taskService.saveTrigger(dto);
        return R.ok();
    }

    @PostMapping("updateTrigger")
    @Operation(operationId = "task:updateTrigger", summary = "触发器更新")
    public R updateTrigger(@RequestBody TaskTriggerDto dto) {
        taskService.updateTrigger(dto);
        return R.ok();
    }

    @PostMapping("removeTrigger")
    @Operation(operationId = "task:removeTrigger", summary = "触发器删除")
    public R removeTrigger(@RequestParam String triggerId) {
        taskService.removeTrigger(triggerId);
        return R.ok();
    }

    @PostMapping("enabledTrigger")
    @Operation(operationId = "task:enabledTrigger", summary = "触发器启用")
    public R enabledTrigger(@RequestParam String triggerId) {
        taskService.enabledTrigger(triggerId);
        return R.ok();
    }

    @PostMapping("disabledTrigger")
    @Operation(operationId = "task:disabledTrigger", summary = "触发器停用")
    public R disabledTrigger(@RequestParam String triggerId) {
        taskService.disabledTrigger(triggerId);
        return R.ok();
    }

    @PostMapping("run")
    @Operation(operationId = "task:run", summary = "执行一次")
    public R run(@RequestParam String beanName) {
        taskService.run(beanName);
        return R.ok();
    }

}
