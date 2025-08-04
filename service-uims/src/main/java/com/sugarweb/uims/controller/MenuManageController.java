package com.sugarweb.uims.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.uims.service.MenuService;
import com.sugarweb.uims.dto.MenuDto;
import com.sugarweb.uims.dto.MenuTreeVo;
import com.sugarweb.uims.dto.MenuQueryDto;
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
public class MenuManageController {

    private final MenuService menuService;

    @GetMapping("detail")
    @Operation(operationId = "menu:detail")
    public R<MenuDto> detail(@NotBlank String id) {
        return R.data(menuService.find(id));
    }

    @GetMapping("list")
    public R<IPage<MenuTreeVo>> list(@RequestParam MenuQueryDto queryDto, PageQuery pageQuery) {
        return R.data(menuService.page(pageQuery, queryDto));
    }

    @PostMapping("save")
    public R<String> save(@RequestBody MenuDto menuDTO) {
        return R.data(menuService.save(menuDTO));
    }

    @PostMapping("update")
    public R<Void> modify(@RequestBody MenuDto menuDTO) {
        menuService.update(menuDTO);
        return R.ok();
    }

    @PostMapping("remove")
    public R<Void> remove(String id) {
        menuService.remove(id);
        return R.ok();
    }

}
