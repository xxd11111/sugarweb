package com.sugarweb.digitalHuman.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.digitalHuman.application.DatasetService;
import com.sugarweb.digitalHuman.application.dto.DatasetDetailDto;
import com.sugarweb.digitalHuman.application.dto.DatasetPageQuery;
import com.sugarweb.digitalHuman.application.dto.DatasetSaveDto;
import com.sugarweb.digitalHuman.application.dto.DatasetUpdateDto;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 数据集管理
 *
 * @author xxd
 * @version 1.0
 */
@Controller
@RequestMapping("/dataset")
@Tag(name = "数据集管理")
public class DatasetController {

    @Resource
    private DatasetService datasetService;

    @GetMapping("/page")
    @Operation(operationId = "dataset:page", summary = "查询数据集分页列表")
    public R<IPage<DatasetDetailDto>> page(DatasetPageQuery query) {
        return R.data(datasetService.page(query));
    }

    @GetMapping("/detail")
    @Operation(operationId = "dataset:detail", summary = "查询数据集详情")
    public R<DatasetDetailDto> detail(String datasetId) {
        return R.data(datasetService.detail(datasetId));
    }

    @PostMapping("/save")
    @Operation(operationId = "dataset:save", summary = "新增数据集")
    public R<DatasetDetailDto> save(DatasetSaveDto saveDto) {
        return R.data(datasetService.save(saveDto));
    }

    @PostMapping("/update")
    @Operation(operationId = "dataset:update", summary = "更新数据集")
    public R<DatasetDetailDto> update(DatasetUpdateDto updateDto) {
        return R.data(datasetService.update(updateDto));
    }

    @PostMapping("/remove")
    @Operation(operationId = "dataset:remove", summary = "删除数据集")
    public R<Void> remove(String datasetId) {
        datasetService.remove(datasetId);
        return R.ok();
    }

}
