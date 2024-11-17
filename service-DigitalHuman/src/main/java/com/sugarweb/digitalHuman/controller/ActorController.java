package com.sugarweb.digitalHuman.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.digitalHuman.application.ActorService;
import com.sugarweb.digitalHuman.application.dto.ActorDetailDto;
import com.sugarweb.digitalHuman.application.dto.ActorPageQuery;
import com.sugarweb.digitalHuman.application.dto.ActorSaveDto;
import com.sugarweb.digitalHuman.application.dto.ActorUpdateDto;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 演员管理
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/actor")
@Tag(name = "演员管理")
public class ActorController {

    @Resource
    private ActorService actorService;

    @GetMapping("/page")
    @Operation(operationId = "actor:page", summary = "分页查询代理列表")
    public R<IPage<ActorDetailDto>> page(ActorPageQuery query) {
        return R.data(actorService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "actor:detail", summary = "查询代理详情")
    public R<ActorDetailDto> detail(String actorId) {
        return R.data(actorService.detail(actorId));
    }

    @PostMapping("/save")
    @Operation(operationId = "actor:save", summary = "新增代理")
    public R<ActorDetailDto> save(ActorSaveDto saveDto) {
        return R.data(actorService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "actor:update", summary = "更新代理")
    public R<ActorDetailDto> update(ActorUpdateDto updateDto) {
        return R.data(actorService.update(updateDto));
    }

    @PostMapping("/remove")
    @Operation(operationId = "actor:remove", summary = "删除代理")
    public R<Void> remove(String actorId) {
        actorService.remove(actorId);
        return R.ok();
    }

}
