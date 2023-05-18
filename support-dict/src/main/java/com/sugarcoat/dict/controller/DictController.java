package com.sugarcoat.dict.controller;

import com.sugarcoat.dict.api.DictService;
import com.sugarcoat.dict.api.DictDTO;
import com.sugarcoat.dict.api.DictItemDTO;
import com.sugarcoat.dict.api.DictQueryVO;
import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParam;
import com.sugarcoat.protocol.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/26
 */
@RestController
@RequestMapping("dict")
@RequiredArgsConstructor
public class DictController {
    private final DictService dictService;

    @PostMapping("saveDict")
    public Result<?> saveDict(@RequestBody DictDTO dictDTO) {
        dictService.save(dictDTO);
        return Result.ok();
    }

    @PostMapping("saveDictItem")
    public Result<?> saveDictItem(@RequestBody DictItemDTO dictItemDTO) {
        dictService.save(dictItemDTO);
        return Result.ok();
    }

    @DeleteMapping("removeDict/{groupIds}")
    public Result<?> removeDict(@PathVariable Set<String> groupIds) {
        dictService.removeDict(groupIds);
        return Result.ok();
    }

    @DeleteMapping("removeDictItem/{dictItems}")
    public Result<?> removeDictItem(@PathVariable Set<String> dictItems) {
        dictService.removeDictItem(dictItems);
        return Result.ok();
    }

    @GetMapping("findDict/{groupId}")
    public Result<DictDTO> findDict(@PathVariable String groupId) {
        return Result.data(dictService.findByGroupId(groupId));
    }

    @GetMapping("pageDict")
    public Result<PageData<DictDTO>> findDictPage(PageParam pageParam, DictQueryVO queryVO) {
        return Result.data(dictService.findDictPage(pageParam, queryVO));
    }
}
