package com.sugarweb.uims.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.uims.service.RoleService;
import com.sugarweb.uims.dto.RoleDto;
import com.sugarweb.uims.dto.RoleVo;
import com.sugarweb.uims.dto.RolePageVo;
import com.sugarweb.uims.dto.RoleQueryDto;
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
public class RoleManageController {

    private final RoleService roleService;

    @GetMapping("detail")
    public R<RoleVo> detail(@NotBlank String id) {
        return R.data(roleService.find(id));
    }

    @GetMapping("list")
    public R<IPage<RolePageVo>> list(PageQuery pageQuery, RoleQueryDto queryDto) {
        return R.data(roleService.page(pageQuery, queryDto));
    }

    @PostMapping("save")
    public R<String> save(@RequestBody RoleDto menuDTO) {
        return R.data(roleService.save(menuDTO));
    }

    @PostMapping("authMenu")
    public R<Void> update(String id, String[] menus) {
        roleService.authMenu(id, List.of(menus));
        return R.ok();
    }

    @PostMapping("remove")
    public R<Void> remove(String id) {
        roleService.remove(id);
        return R.ok();
    }

}
