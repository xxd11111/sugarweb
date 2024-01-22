package com.sugarcoat.support.dict;

import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.support.dict.application.dto.DictionaryDto;
import com.sugarcoat.support.dict.application.dto.DictionaryQueryDto;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.common.Result;
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

	@PostMapping("save")
	public Result<Void> save(@RequestBody DictionaryDto dto) {
		dictionaryService.save(dto);
		return Result.ok();
	}

	@DeleteMapping("remove/{ids}")
	public Result<Void> remove(@PathVariable Set<String> ids) {
		dictionaryService.remove(ids);
		return Result.ok();
	}

	@DeleteMapping("removeGroup/{group}")
	public Result<Void> removeGroup(@PathVariable String group) {
		dictionaryService.removeGroup(group);
		return Result.ok();
	}

	@GetMapping("findOne/{id}")
	public Result<DictionaryDto> findOne(@PathVariable String id) {
		return Result.data(dictionaryService.findOne(id));
	}

	@GetMapping("findDictionary/{dictionaryId}")
	public Result<DictionaryDto> findDictionary(@PathVariable String dictionaryId) {
		return Result.data(dictionaryService.findOne(dictionaryId));
	}

	@GetMapping("findPage")
	public Result<PageData<DictionaryDto>> findPage(PageDto pageDto, DictionaryQueryDto queryDto) {
		return Result.data(dictionaryService.findPage(pageDto, queryDto));
	}

	@GetMapping("findDictionaryPage")
	public Result<PageData<DictionaryDto>> findDictPage(PageDto pageDto, DictionaryQueryDto queryDto) {
		return Result.data(dictionaryService.findPage(pageDto, queryDto));
	}

}
