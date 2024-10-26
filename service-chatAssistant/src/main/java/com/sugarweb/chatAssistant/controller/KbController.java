package com.sugarweb.chatAssistant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 知识库管理knowledgeBase
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/kb")
@Tag(name = "kb管理")
public class KbController {

    @Resource
    private com.sugarweb.chatAssistant.controller.KbService kbService;

    @GetMapping("/page")
    @Operation(operationId = "kb:page", summary = "分页查询代理列表")
    public R<IPage<KbDetailDto>> page(KbPageQuery query) {
        return R.data(kbService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "kb:detail", summary = "查询代理详情")
    public R<KbDetailDto> detail(String kbId) {
        return R.data(kbService.detail(kbId));
    }

    @PostMapping("/save")
    @Operation(operationId = "kb:save", summary = "新增代理")
    public R<KbDetailDto> save(KbSaveDto saveDto) {
        return R.data(kbService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "kb:update", summary = "更新代理")
    public R<KbDetailDto> update(KbUpdateDto updateDto) {
        return R.data(kbService.update(updateDto));
    }

}
