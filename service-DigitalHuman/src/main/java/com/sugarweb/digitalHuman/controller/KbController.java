package com.sugarweb.digitalHuman.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.digitalHuman.application.KbService;
import com.sugarweb.digitalHuman.application.dto.KbDetailDto;
import com.sugarweb.digitalHuman.application.dto.KbPageQuery;
import com.sugarweb.digitalHuman.application.dto.KbSaveDto;
import com.sugarweb.digitalHuman.application.dto.KbUpdateDto;
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
@Tag(name = "知识库管理")
public class KbController {

    @Resource
    private KbService kbService;

    @GetMapping("/page")
    @Operation(operationId = "kb:page", summary = "查询知识库分页列表")
    public R<IPage<KbDetailDto>> page(KbPageQuery query) {
        return R.data(kbService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "kb:detail", summary = "查询知识库详情")
    public R<KbDetailDto> detail(String kbId) {
        return R.data(kbService.detail(kbId));
    }

    @PostMapping("/save")
    @Operation(operationId = "kb:save", summary = "新增知识库")
    public R<KbDetailDto> save(KbSaveDto saveDto) {
        return R.data(kbService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "kb:update", summary = "更新知识库")
    public R<KbDetailDto> update(KbUpdateDto updateDto) {
        return R.data(kbService.update(updateDto));
    }

    @PostMapping("/remove")
    @Operation(operationId = "kb:remove", summary = "删除知识库")
    public R<Void> remove(String kbId) {
        kbService.remove(kbId);
        return R.ok();
    }

}
