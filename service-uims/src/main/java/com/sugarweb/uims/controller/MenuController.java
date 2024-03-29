package com.sugarweb.uims.controller;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.Result;
import com.sugarweb.uims.application.dto.MenuDto;
import com.sugarweb.uims.application.vo.MenuTreeVo;
import com.sugarweb.uims.application.dto.MenuQueryDto;
import com.sugarweb.uims.application.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Tag(name = "菜单管理", description = "这是菜单管理")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("{id}")
    @Operation(operationId = "menu:findOne")
    public Result<MenuDto> findOne(@NotBlank @PathVariable String id) {
        return Result.data(menuService.find(id));
    }

    @GetMapping("page")
    public Result<PageData<MenuTreeVo>> page(@RequestParam MenuQueryDto menuQueryDTOVO) {
        return Result.data(menuService.page(menuQueryDTOVO));
    }

    @PostMapping("save")
    public Result<String> save(@RequestBody MenuDto menuDTO) {
        return Result.data(menuService.save(menuDTO));
    }

    @PostMapping("modify")
    public Result<Void> modify(@RequestBody MenuDto menuDTO) {
        menuService.modify(menuDTO);
        return Result.ok();
    }

    @PostMapping("remove")
    public Result<Void> remove(String id) {
        menuService.remove(id);
        return Result.ok();
    }

}
