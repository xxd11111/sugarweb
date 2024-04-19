package com.sugarweb.dictionary.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.dictionary.application.DictionaryService;
import com.sugarweb.dictionary.application.dto.DictionaryDto;
import com.sugarweb.dictionary.application.dto.DictionaryQueryDto;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
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
	public R<Void> save(@RequestBody DictionaryDto dto) {
		dictionaryService.save(dto);
		return R.ok();
	}

	@DeleteMapping("remove/{ids}")
	public R<Void> remove(@PathVariable Set<String> ids) {
		dictionaryService.removeByIds(ids);
		return R.ok();
	}

	@DeleteMapping("removeGroup/{group}")
	public R<Void> removeGroup(@PathVariable String group) {
		dictionaryService.removeByGroup(group);
		return R.ok();
	}

	@GetMapping("findOne/{id}")
	public R<DictionaryDto> findOne(@PathVariable String id) {
		return R.data(dictionaryService.findById(id).orElse(null));
	}

	@GetMapping("findDictionary/{dictionaryId}")
	public R<DictionaryDto> findDictionary(@PathVariable String dictionaryId) {
		return R.data(dictionaryService.findById(dictionaryId).orElse(null));
	}

	@GetMapping("findPage")
	public R<IPage<DictionaryDto>> findPage(PageQuery pageQuery, DictionaryQueryDto queryDto) {
		return R.data(dictionaryService.findPage(pageQuery, queryDto));
	}

}
