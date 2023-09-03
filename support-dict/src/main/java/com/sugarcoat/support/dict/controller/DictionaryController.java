package com.sugarcoat.support.dict.controller;

import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.support.dict.application.dto.DictionaryGroupDto;
import com.sugarcoat.support.dict.application.dto.DictionaryDto;
import com.sugarcoat.support.dict.application.dto.DictQueryDto;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.api.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 字典控制器
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/26
 */
@RestController
@RequestMapping("dictionary")
@RequiredArgsConstructor
public class DictionaryController {

	private final DictionaryService dictionaryService;

	@PostMapping("saveDictionaryGroup")
	public Result<Void> saveDict(@RequestBody DictionaryGroupDto dictionaryGroupDto) {
		dictionaryService.save(dictionaryGroupDto);
		return Result.ok();
	}

	@PostMapping("saveDictionary")
	public Result<Void> saveDictItem(@RequestBody DictionaryDto dictionaryDto) {
		dictionaryService.save(dictionaryDto);
		return Result.ok();
	}

	@DeleteMapping("removeDictionaryGroup/{groupIds}")
	public Result<Void> removeDict(@PathVariable Set<String> groupIds) {
		dictionaryService.removeDictionaryGroup(groupIds);
		return Result.ok();
	}

	@DeleteMapping("removeDictionary/{dictionaryIds}")
	public Result<Void> removeDictionary(@PathVariable Set<String> dictionaryIds) {
		dictionaryService.removeDictionary(dictionaryIds);
		return Result.ok();
	}

	@GetMapping("findDictionaryGroup/{groupId}")
	public Result<DictionaryGroupDto> findDictionaryGroup(@PathVariable String groupId) {
		return Result.data(dictionaryService.findByGroupId(groupId));
	}

	@GetMapping("findDictionary/{dictionaryId}")
	public Result<DictionaryDto> findDictionary(@PathVariable String dictionaryId) {
		return Result.data(dictionaryService.findByDictionaryId(dictionaryId));
	}

	@GetMapping("findByGroupCode/{groupCode}")
	public Result<DictionaryGroupDto> findByGroupCode(@PathVariable String groupCode) {
		return Result.data(dictionaryService.findByGroupCode(groupCode));
	}

	@GetMapping("findDictionaryPage")
	public Result<PageData<DictionaryGroupDto>> findDictPage(PageDto pageDto, DictQueryDto queryDto) {
		return Result.data(dictionaryService.findDictPage(pageDto, queryDto));
	}

}
