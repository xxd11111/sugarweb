package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.RoleDto;
import com.sugarcoat.uims.application.dto.RoleVo;
import com.sugarcoat.uims.application.dto.RolePageVO;
import com.sugarcoat.uims.application.dto.RoleQueryDTO;
import com.sugarcoat.uims.application.service.RoleService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author xxd
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("{id}")
    public Result<RoleVo> findOne(@NotBlank @PathVariable String id) {
        return Result.data(roleService.find(id));
    }

    @GetMapping("page")
    public Result<PageData<RolePageVO>> page(@RequestParam RoleQueryDTO roleQueryDTO) {
        return Result.data(roleService.page(roleQueryDTO));
    }

    @PostMapping("save")
    public Result<String> save(@RequestBody RoleDto menuDTO) {
        return Result.data(roleService.save(menuDTO));
    }

    @PostMapping("update")
    public Result<Void> update(@RequestBody RoleDto menuDTO) {
        roleService.modify(menuDTO);
        return Result.ok();
    }

    @PostMapping("associateMenu")
    public Result<Void> update(String[] menus) {
        roleService.associateMenu(List.of(menus));
        return Result.ok();
    }

    @PostMapping("remove")
    public Result<Void> remove(String id) {
        roleService.remove(id);
        return Result.ok();
    }

}
