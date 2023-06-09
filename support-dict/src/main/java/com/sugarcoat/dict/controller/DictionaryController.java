package com.sugarcoat.dict.controller;

import com.sugarcoat.dict.application.DictionaryService;
import com.sugarcoat.dict.application.DictionaryGroupDTO;
import com.sugarcoat.dict.application.DictionaryDTO;
import com.sugarcoat.dict.application.DictQueryVO;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import com.sugarcoat.api.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 字典控制器
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/26
 */
@RestController
@RequestMapping("dictionary")
@RequiredArgsConstructor
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @PostMapping("saveDictionaryGroup")
    public Result<Void> saveDict(@RequestBody DictionaryGroupDTO dictionaryGroupDTO) {
        dictionaryService.save(dictionaryGroupDTO);
        return Result.ok();
    }

    @PostMapping("saveDictionary")
    public Result<Void> saveDictItem(@RequestBody DictionaryDTO dictionaryDTO) {
        dictionaryService.save(dictionaryDTO);
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
    public Result<DictionaryGroupDTO> findDictionaryGroup(@PathVariable String groupId) {
        return Result.data(dictionaryService.findByGroupId(groupId));
    }

    @GetMapping("findDictionary/{dictionaryId}")
    public Result<DictionaryDTO> findDictionary(@PathVariable String dictionaryId) {
        return Result.data(dictionaryService.findByDictionaryId(dictionaryId));
    }

    @GetMapping("findByGroupCode/{groupCode}")
    public Result<DictionaryGroupDTO> findByGroupCode(@PathVariable String groupCode) {
        return Result.data(dictionaryService.findByGroupCode(groupCode));
    }

    @GetMapping("findDictionaryPage")
    public Result<PageData<DictionaryGroupDTO>> findDictPage(PageParameter pageParameter, DictQueryVO queryVO) {
        return Result.data(dictionaryService.findDictPage(pageParameter, queryVO));
    }
}
