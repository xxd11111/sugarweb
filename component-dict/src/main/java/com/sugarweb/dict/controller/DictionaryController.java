package com.sugarweb.dict.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.dict.application.DictService;
import com.sugarweb.dict.application.dto.DictGroupDto;
import com.sugarweb.dict.application.dto.DictItemDto;
import com.sugarweb.dict.application.dto.DictQuery;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 字典控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("dictionary")
@RequiredArgsConstructor
public class DictionaryController {

	private final DictService dictService;

	@PostMapping("save")
	public R<Void> save(@RequestBody DictItemDto dto) {
		dictService.saveItem(dto);
		return R.ok();
	}

	@PostMapping("save")
	public R<Void> update(@RequestBody DictItemDto dto) {
		dictService.updateItem(dto);
		return R.ok();
	}

	@PostMapping("removeGroupById/{id}")
	public R<Void> remove(@PathVariable String id) {
		dictService.removeGroupById(id);
		return R.ok();
	}

	@PostMapping("removeItemById/{id}")
	public R<Void> removeGroup(@PathVariable String id) {
		dictService.removeItemById(id);
		return R.ok();
	}

	@GetMapping("getItemById/{id}")
	public R<DictItemDto> findOne(@PathVariable String id) {
		return R.data(dictService.getItemById(id));
	}

	@GetMapping("groupPage")
	public R<IPage<DictGroupDto>> groupPage(PageQuery pageQuery, DictQuery queryDto) {
		return R.data(dictService.groupPage(pageQuery, queryDto));
	}

}
