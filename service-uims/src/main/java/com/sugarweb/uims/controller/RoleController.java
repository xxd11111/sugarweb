package com.sugarweb.uims.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.uims.domain.dto.RoleDto;
import com.sugarweb.uims.domain.dto.RoleVo;
import com.sugarweb.uims.domain.dto.RolePageVo;
import com.sugarweb.uims.domain.dto.RoleQueryDto;
import com.sugarweb.uims.application.RoleService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("{id}")
    public R<RoleVo> findOne(@NotBlank @PathVariable String id) {
        return R.data(roleService.find(id));
    }

    @GetMapping("page")
    public R<IPage<RolePageVo>> page(PageQuery pageQuery, RoleQueryDto queryDto) {
        return R.data(roleService.page(pageQuery, queryDto));
    }

    @PostMapping("save")
    public R<String> save(@RequestBody RoleDto menuDTO) {
        return R.data(roleService.save(menuDTO));
    }

    @PostMapping("associateMenu")
    public R<Void> update(String id, String[] menus) {
        roleService.associateMenu(id, List.of(menus));
        return R.ok();
    }

    @PostMapping("remove")
    public R<Void> remove(String id) {
        roleService.remove(id);
        return R.ok();
    }

}
