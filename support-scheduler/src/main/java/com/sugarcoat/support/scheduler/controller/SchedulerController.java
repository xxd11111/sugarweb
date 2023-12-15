package com.sugarcoat.support.scheduler.controller;

import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.support.scheduler.service.SchedulerService;
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
    public SchedulerService schedulerService;

    @GetMapping("findAll")
    @Operation(operationId = "scheduler:findAll", summary = "findAll")
    public Result findAll() {
        return Result.data(schedulerService.getAll());
    }

    @PostMapping("add")
    @Operation(operationId = "scheduler:add", summary = "add")
    public Result add(@RequestBody SchedulerTaskDto dto) {
        schedulerService.add(dto);
        return Result.ok();
    }

    @PostMapping("update")
    @Operation(operationId = "scheduler:update", summary = "update")
    public Result update(@RequestBody SchedulerTaskDto dto) {
        schedulerService.update(dto);
        return Result.ok();
    }

    @PostMapping("delete/{name}")
    @Operation(operationId = "scheduler:delete", summary = "delete")
    public Result delete(@PathVariable String name) {
        schedulerService.delete(name);
        return Result.ok();
    }

    @PostMapping("pause/{name}")
    @Operation(operationId = "scheduler:pause", summary = "pause")
    public Result pause(@PathVariable String name) {
        schedulerService.pause(name);
        return Result.ok();
    }

    @PostMapping("resume/{name}")
    @Operation(operationId = "scheduler:resume", summary = "resume")
    public Result resume(@PathVariable String name) {
        schedulerService.resume(name);
        return Result.ok();
    }

    @PostMapping("run")
    @Operation(operationId = "scheduler:run", summary = "run")
    public Result run(@RequestBody SchedulerTaskDto dto) {
        schedulerService.run(dto);
        return Result.ok();
    }

}
