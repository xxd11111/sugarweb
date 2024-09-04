package com.sugarweb.uims.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.uims.application.MenuService;
import com.sugarweb.uims.application.dto.MenuDto;
import com.sugarweb.uims.application.dto.MenuTreeVo;
import com.sugarweb.uims.application.dto.MenuQueryDto;
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
    public R<MenuDto> findOne(@NotBlank @PathVariable String id) {
        return R.data(menuService.find(id));
    }

    @GetMapping("page")
    public R<IPage<MenuTreeVo>> page(@RequestParam MenuQueryDto queryDto, PageQuery pageQuery) {
        return R.data(menuService.page(pageQuery, queryDto));
    }

    @PostMapping("save")
    public R<String> save(@RequestBody MenuDto menuDTO) {
        return R.data(menuService.save(menuDTO));
    }

    @PostMapping("modify")
    public R<Void> modify(@RequestBody MenuDto menuDTO) {
        menuService.modify(menuDTO);
        return R.ok();
    }

    @PostMapping("remove")
    public R<Void> remove(String id) {
        menuService.remove(id);
        return R.ok();
    }

}
