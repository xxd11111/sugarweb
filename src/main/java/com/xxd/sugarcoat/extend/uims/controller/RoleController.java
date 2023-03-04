package com.xxd.sugarcoat.extend.uims.controller;

import com.xxd.sugarcoat.extend.uims.application.dto.RoleDTO;
import com.xxd.sugarcoat.extend.uims.application.dto.RolePageDTO;
import com.xxd.sugarcoat.extend.uims.application.dto.StatusDTO;
import com.xxd.sugarcoat.extend.uims.application.service.RoleService;
import com.xxd.sugarcoat.support.common.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;

    @GetMapping("{id}")
    public R findOne(@NotBlank @PathVariable String id) {
        return null;
    }

    @GetMapping("page")
    public R page(@RequestParam RolePageDTO pageDTO) {
        return null;
    }

    @PostMapping("save")
    public R save(@RequestBody RoleDTO menuDTO) {
        return null;
    }

    @PostMapping("update")
    public R update(@RequestBody RoleDTO menuDTO) {
        return null;
    }

    @PostMapping("status")
    public R status(@RequestBody StatusDTO statusDTO) {
        return null;
    }
}
