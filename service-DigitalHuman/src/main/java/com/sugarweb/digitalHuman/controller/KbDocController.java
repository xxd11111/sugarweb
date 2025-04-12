package com.sugarweb.digitalHuman.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.digitalHuman.service.dto.DocParseDto;
import com.sugarweb.digitalHuman.service.KbDocService;
import com.sugarweb.digitalHuman.service.dto.KbDocDetailDto;
import com.sugarweb.digitalHuman.service.dto.KbDocPageQuery;
import com.sugarweb.digitalHuman.service.dto.DocSaveDto;
import com.sugarweb.digitalHuman.service.dto.DocRenameDto;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

/**
 * 知识库文档管理
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/doc")
@Tag(name = "doc管理")
public class KbDocController {

    @Resource
    private KbDocService kbDocService;

    @GetMapping("/page")
    @Operation(operationId = "document:page", summary = "查询文档分页列表")
    public R<IPage<KbDocDetailDto>> page(KbDocPageQuery query) {
        return R.data(kbDocService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "document:detail", summary = "查询文档详情")
    public R<KbDocDetailDto> detail(String docId) {
        return R.data(kbDocService.detail(docId));
    }

    @PostMapping("/save")
    @Operation(operationId = "document:save", summary = "新增文档")
    public R<KbDocDetailDto> save(DocSaveDto saveDto) {
        return R.data(kbDocService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "document:update", summary = "更新文档")
    public R<KbDocDetailDto> update(DocRenameDto updateDto) {
        return R.data(kbDocService.update(updateDto));
    }

    @PostMapping("/remove")
    @Operation(operationId = "document:remove", summary = "删除文档")
    public R<Void> remove(@RequestPart List<String> docIds) {
        kbDocService.remove(docIds);
        return R.ok();
    }

    @PostMapping("/parse")
    @Operation(operationId = "document:parse", summary = "解析文档")
    public R<Void> parse(DocParseDto docParseDto) {
        kbDocService.parseStart(docParseDto.getKbId(), docParseDto.getDocIds());
        return R.ok();
    }

}
