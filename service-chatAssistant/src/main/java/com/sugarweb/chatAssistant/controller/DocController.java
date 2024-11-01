package com.sugarweb.chatAssistant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.chatAssistant.application.DocService;
import com.sugarweb.chatAssistant.application.dto.DocDetailDto;
import com.sugarweb.chatAssistant.application.dto.DocPageQuery;
import com.sugarweb.chatAssistant.application.dto.DocSaveDto;
import com.sugarweb.chatAssistant.application.dto.DocUpdateDto;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 知识库文档管理
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/doc")
@Tag(name = "doc管理")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/page")
    @Operation(operationId = "doc:page", summary = "查询文档分页列表")
    public R<IPage<DocDetailDto>> page(DocPageQuery query) {
        return R.data(docService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "doc:detail", summary = "查询文档详情")
    public R<DocDetailDto> detail(String docId) {
        return R.data(docService.detail(docId));
    }

    @PostMapping("/save")
    @Operation(operationId = "doc:save", summary = "新增文档")
    public R<DocDetailDto> save(DocSaveDto saveDto) {
        return R.data(docService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "doc:update", summary = "更新文档")
    public R<DocDetailDto> update(DocUpdateDto updateDto) {
        return R.data(docService.update(updateDto));
    }

    @PostMapping("/parse")
    @Operation(operationId = "doc:parse", summary = "解析文档")
    public R<Void> parse(DocParseDto docParseDto) {
        if ("start".equals(docParseDto.getParseOperate())) {
            docService.parseStart(docParseDto.getDocIds());
        } else if ("stop".equals(docParseDto.getParseOperate())){
            docService.parseStop(docParseDto.getDocIds());
        }
        return R.ok();
    }

}
