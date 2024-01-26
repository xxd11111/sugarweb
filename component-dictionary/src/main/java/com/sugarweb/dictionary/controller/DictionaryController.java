package com.sugarweb.dictionary.controller;

import com.sugarweb.dictionary.application.DictionaryService;
import com.sugarweb.dictionary.application.dto.DictionaryDto;
import com.sugarweb.dictionary.application.dto.DictionaryQueryDto;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
	public Result<Void> save(@RequestBody DictionaryDto dto) {
		dictionaryService.save(dto);
		return Result.ok();
	}

	@DeleteMapping("remove/{ids}")
	public Result<Void> remove(@PathVariable Set<String> ids) {
		dictionaryService.removeByIds(ids);
		return Result.ok();
	}

	@DeleteMapping("removeGroup/{group}")
	public Result<Void> removeGroup(@PathVariable String group) {
		dictionaryService.removeByGroup(group);
		return Result.ok();
	}

	@GetMapping("findOne/{id}")
	public Result<DictionaryDto> findOne(@PathVariable String id) {
		return Result.data(dictionaryService.findById(id).orElse(null));
	}

	@GetMapping("findDictionary/{dictionaryId}")
	public Result<DictionaryDto> findDictionary(@PathVariable String dictionaryId) {
		return Result.data(dictionaryService.findById(dictionaryId).orElse(null));
	}

	@GetMapping("findPage")
	public Result<PageData<DictionaryDto>> findPage(PageQuery pageQuery, DictionaryQueryDto queryDto) {
		return Result.data(dictionaryService.findPage(pageQuery, queryDto));
	}

}
