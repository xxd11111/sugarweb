package com.sugarweb.dictionary.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.dictionary.application.DictionaryService;
import com.sugarweb.dictionary.application.dto.DictGroupDto;
import com.sugarweb.dictionary.application.dto.DictItemDto;
import com.sugarweb.dictionary.application.dto.DictQuery;
import com.sugarweb.dictionary.domain.DictGroup;
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

	private final DictionaryService dictionaryService;

	@PostMapping("save")
	public R<Void> save(@RequestBody DictItemDto dto) {
		dictionaryService.saveItem(dto);
		return R.ok();
	}

	@PostMapping("save")
	public R<Void> update(@RequestBody DictItemDto dto) {
		dictionaryService.updateItem(dto);
		return R.ok();
	}

	@PostMapping("removeGroupById/{id}")
	public R<Void> remove(@PathVariable String id) {
		dictionaryService.removeGroupById(id);
		return R.ok();
	}

	@PostMapping("removeItemById/{id}")
	public R<Void> removeGroup(@PathVariable String id) {
		dictionaryService.removeItemById(id);
		return R.ok();
	}

	@GetMapping("getItemById/{id}")
	public R<DictItemDto> findOne(@PathVariable String id) {
		return R.data(dictionaryService.getItemById(id));
	}

	@GetMapping("groupPage")
	public R<IPage<DictGroupDto>> groupPage(PageQuery pageQuery, DictQuery queryDto) {
		return R.data(dictionaryService.groupPage(pageQuery, queryDto));
	}

}
