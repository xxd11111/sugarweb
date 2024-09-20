package com.sugarweb.dict.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.dict.application.DictService;
import com.sugarweb.dict.application.dto.DictGroupDto;
import com.sugarweb.dict.application.dto.DictItemDto;
import com.sugarweb.dict.application.dto.DictQuery;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("dictionary")
@RequiredArgsConstructor
@Tag(name = "字典管理")
public class DictionaryController {

	private final DictService dictService;

	@PostMapping("saveItem")
	@Operation(operationId = "dict:saveItem", summary = "新增字典项")
	public R<Void> saveItem(@RequestBody DictItemDto dto) {
		dictService.saveItem(dto);
		return R.ok();
	}

	@PostMapping("updateItem")
	@Operation(operationId = "dict:updateItem", summary = "更新字典项")
	public R<Void> updateItem(@RequestBody DictItemDto dto) {
		dictService.updateItem(dto);
		return R.ok();
	}

	@PostMapping("removeItemById")
	@Operation(operationId = "dict:removeItemById", summary = "删除字典项")
	public R<Void> removeGroup(@RequestParam String id) {
		dictService.removeItemById(id);
		return R.ok();
	}

	@GetMapping("getItemById")
	@Operation(operationId = "dict:getItemById", summary = "字典项详情")
	public R<DictItemDto> findOne(@RequestParam String id) {
		return R.data(dictService.getItemById(id));
	}

	@GetMapping("listDictItem")
	@Operation(operationId = "dict:listDictItem", summary = "字典项列表")
	public R<List<DictItemDto>> listDictItem() {
		return R.data(dictService.listItem());
	}

	@GetMapping("itemPage")
	@Operation(operationId = "dict:itemPage", summary = "字典项分页")
	public R<IPage<DictItemDto>> itemPage(PageQuery pageQuery, DictQuery queryDto) {
		return R.data(dictService.itemPage(pageQuery, queryDto));
	}

	@PostMapping("saveGroup")
	@Operation(operationId = "dict:saveGroup", summary = "新增字典组")
	public R<Void> saveGroup(@RequestBody DictGroupDto dto) {
		dictService.saveGroup(dto);
		return R.ok();
	}

	@PostMapping("updateGroup")
	@Operation(operationId = "dict:updateGroup", summary = "更新字典组")
	public R<Void> updateGroup(@RequestBody DictGroupDto dto) {
		dictService.updateGroup(dto);
		return R.ok();
	}

	@PostMapping("removeGroupById/{id}")
	@Operation(operationId = "dict:removeGroupById", summary = "删除字典组")
	public R<Void> removeGroupById(@PathVariable String id) {
		dictService.removeGroupById(id);
		return R.ok();
	}

	@GetMapping("groupPage")
	@Operation(operationId = "dict:groupPage", summary = "字典组分页")
	public R<IPage<DictGroupDto>> groupPage(PageQuery pageQuery, DictQuery queryDto) {
		return R.data(dictService.groupPage(pageQuery, queryDto));
	}

}
