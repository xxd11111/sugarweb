package com.sugarcoat.support.scheduler.controller;

import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.support.scheduler.service.SchedulerServiceImpl;
import com.sugarcoat.support.scheduler.service.SchedulerTaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务控制器
 *
 * @author 许向东
 * @date 2023/10/18
 */
@RestController
@RequestMapping("/scheduler")
@Tag(name = "定时任务")
public class SchedulerController {

    @Resource
    public SchedulerServiceImpl schedulerServiceImpl;

    @GetMapping("findAll")
    @Operation(operationId = "scheduler:findAll", summary = "findAll")
    public Result findAll() {
        return Result.data(schedulerServiceImpl.getAll());
    }

    @PostMapping("add")
    @Operation(operationId = "scheduler:add", summary = "add")
    public Result add(@RequestBody SchedulerTaskDto dto) {
        schedulerServiceImpl.add(dto);
        return Result.ok();
    }

    @PostMapping("update")
    @Operation(operationId = "scheduler:update", summary = "update")
    public Result update(@RequestBody SchedulerTaskDto dto) {
        schedulerServiceImpl.update(dto);
        return Result.ok();
    }

    @PostMapping("delete/{id}")
    @Operation(operationId = "scheduler:delete", summary = "delete")
    public Result delete(@PathVariable String id) {
        schedulerServiceImpl.delete(id);
        return Result.ok();
    }

    @PostMapping("pause/{id}")
    @Operation(operationId = "scheduler:pause", summary = "pause")
    public Result pause(@PathVariable String id) {
        schedulerServiceImpl.pause(id);
        return Result.ok();
    }

    @PostMapping("resume/{id}")
    @Operation(operationId = "scheduler:resume", summary = "resume")
    public Result resume(@PathVariable String id) {
        schedulerServiceImpl.resume(id);
        return Result.ok();
    }

    @PostMapping("run/{id}")
    @Operation(operationId = "scheduler:run", summary = "run")
    public Result run(@PathVariable String id) {
        schedulerServiceImpl.run(id);
        return Result.ok();
    }

}
