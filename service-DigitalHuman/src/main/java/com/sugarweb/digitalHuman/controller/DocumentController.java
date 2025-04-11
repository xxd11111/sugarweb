package com.sugarweb.digitalHuman.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.digitalHuman.service.dto.DocumentParseDto;
import com.sugarweb.digitalHuman.service.DocumentService;
import com.sugarweb.digitalHuman.service.dto.DocumentDetailDto;
import com.sugarweb.digitalHuman.service.dto.DocumentPageQuery;
import com.sugarweb.digitalHuman.service.dto.DocumentSaveDto;
import com.sugarweb.digitalHuman.service.dto.DocumentUpdateDto;
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
public class DocumentController {

    @Resource
    private DocumentService documentService;

    @GetMapping("/page")
    @Operation(operationId = "document:page", summary = "查询文档分页列表")
    public R<IPage<DocumentDetailDto>> page(DocumentPageQuery query) {
        return R.data(documentService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "document:detail", summary = "查询文档详情")
    public R<DocumentDetailDto> detail(String docId) {
        return R.data(documentService.detail(docId));
    }

    @PostMapping("/save")
    @Operation(operationId = "document:save", summary = "新增文档")
    public R<DocumentDetailDto> save(DocumentSaveDto saveDto) {
        return R.data(documentService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "document:update", summary = "更新文档")
    public R<DocumentDetailDto> update(DocumentUpdateDto updateDto) {
        return R.data(documentService.update(updateDto));
    }

    @PostMapping("/remove")
    @Operation(operationId = "document:remove", summary = "删除文档")
    public R<Void> remove(@RequestPart List<String> docIds) {
        documentService.remove(docIds);
        return R.ok();
    }

    @PostMapping("/parse")
    @Operation(operationId = "document:parse", summary = "解析文档")
    public R<Void> parse(DocumentParseDto documentParseDto) {
        documentService.parseStart(documentParseDto.getDatasetId(), documentParseDto.getDocumentIds());
        return R.ok();
    }

}
