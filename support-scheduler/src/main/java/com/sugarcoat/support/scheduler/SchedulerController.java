package com.sugarcoat.support.scheduler;

import com.sugarcoat.protocol.common.Result;
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
    public Result findAll(){
        return Result.data(schedulerService.getAll());
    }

    @PostMapping("add")
    @Operation(operationId = "scheduler:add", summary = "add")
    public Result add(SchedulerTaskDto dto){
        schedulerService.add(dto);
        return Result.ok();
    }

    @PostMapping("update")
    @Operation(operationId = "scheduler:update", summary = "update")
    public Result update(SchedulerTaskDto dto){
        schedulerService.update(dto);
        return Result.ok();
    }

    @PostMapping("enable/{name}")
    @Operation(operationId = "scheduler:enable", summary = "enable")
    public Result enable(@PathVariable String name){
        schedulerService.enable(name);
        return Result.ok();
    }

    @PostMapping("disable/{name}")
    @Operation(operationId = "scheduler:disable", summary = "disable")
    public Result disable(@PathVariable String name){
        schedulerService.disable(name);
        return Result.ok();
    }

    @PostMapping("delete/{name}")
    @Operation(operationId = "scheduler:delete", summary = "delete")
    public Result delete(@PathVariable String name){
        schedulerService.delete(name);
        return Result.ok();
    }

    @PostMapping("pause/{name}")
    @Operation(operationId = "scheduler:pause", summary = "pause")
    public Result pause(@PathVariable String name){
        schedulerService.pause(name);
        return Result.ok();
    }

    @PostMapping("resume/{name}")
    @Operation(operationId = "scheduler:resume", summary = "resume")
    public Result resume(@PathVariable String name){
        schedulerService.resume(name);
        return Result.ok();
    }

    @PostMapping("interrupt/{name}")
    @Operation(operationId = "scheduler:interrupt", summary = "interrupt")
    public Result interrupt(@PathVariable String name){
        schedulerService.interrupt(name);
        return Result.ok();
    }

    @PostMapping("run/{name}")
    @Operation(operationId = "scheduler:run", summary = "run")
    public Result run(@PathVariable String name){
        schedulerService.run(name);
        return Result.ok();
    }

}
