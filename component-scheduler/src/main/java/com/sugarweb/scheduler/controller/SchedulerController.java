package com.sugarweb.scheduler.controller;

import com.sugarweb.scheduler.application.SchedulerService;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.Result;
import com.sugarweb.scheduler.application.SchedulerQueryDto;
import com.sugarweb.scheduler.application.SchedulerTaskDto;
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
@RequestMapping("/scheduler")
@Tag(name = "定时任务")
public class SchedulerController {

    @Resource
    public SchedulerService schedulerService;

    @GetMapping("page")
    @Operation(operationId = "scheduler:findAll", summary = "分页查询")
    public Result page(PageQuery pageQuery, SchedulerQueryDto queryDto) {
        return Result.data(schedulerService.page(pageQuery, queryDto));
    }

    @PostMapping("add")
    @Operation(operationId = "scheduler:add", summary = "新增")
    public Result add(@RequestBody SchedulerTaskDto dto) {
        schedulerService.add(dto);
        return Result.ok();
    }

    @PostMapping("update")
    @Operation(operationId = "scheduler:update", summary = "更新")
    public Result update(@RequestBody SchedulerTaskDto dto) {
        schedulerService.update(dto);
        return Result.ok();
    }

    @PostMapping("delete/{id}")
    @Operation(operationId = "scheduler:delete", summary = "删除")
    public Result delete(@PathVariable String id) {
        schedulerService.delete(id);
        return Result.ok();
    }

    @PostMapping("pause/{id}")
    @Operation(operationId = "scheduler:pause", summary = "停用")
    public Result pause(@PathVariable String id) {
        schedulerService.pause(id);
        return Result.ok();
    }

    @PostMapping("resume/{id}")
    @Operation(operationId = "scheduler:resume", summary = "启用")
    public Result resume(@PathVariable String id) {
        schedulerService.resume(id);
        return Result.ok();
    }

    @PostMapping("run/{id}")
    @Operation(operationId = "scheduler:run", summary = "执行一次")
    public Result run(@PathVariable String id) {
        schedulerService.run(id);
        return Result.ok();
    }

}
