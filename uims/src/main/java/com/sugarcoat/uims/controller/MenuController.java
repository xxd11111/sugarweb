package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.MenuDTO;
import com.sugarcoat.uims.application.dto.MenuTreeVO;
import com.sugarcoat.uims.application.dto.MenuQueryDTO;
import com.sugarcoat.uims.application.service.MenuService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单控制器
 *
 * @author xxd
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("{id}")
    public Result<MenuDTO> findOne(@NotBlank @PathVariable String id) {
        return Result.data(menuService.find(id));
    }

    @GetMapping("page")
    public Result<PageData<MenuTreeVO>> page(@RequestParam MenuQueryDTO menuQueryDTOVO) {
        return Result.data(menuService.page(menuQueryDTOVO));
    }

    @PostMapping("save")
    public Result<String> save(@RequestBody MenuDTO menuDTO) {
        return Result.data(menuService.save(menuDTO));
    }

    @PostMapping("modify")
    public Result<Void> modify(@RequestBody MenuDTO menuDTO) {
        menuService.modify(menuDTO);
        return Result.ok();
    }

    @PostMapping("associateApi")
    public Result<Void> associateApi(String[] apis) {
        menuService.associateApi(List.of(apis));
        return Result.ok();
    }

    @PostMapping("remove")
    public Result<Void> remove(String id) {
        menuService.remove(id);
        return Result.ok();
    }

}
