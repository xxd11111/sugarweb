package com.sugarcoat.uims.demo.controller;

import com.sugarcoat.protocol.Result;
import com.sugarcoat.uims.demo.application.dto.RoleDTO;
import com.sugarcoat.uims.demo.application.dto.RolePageDTO;
import com.sugarcoat.uims.demo.application.dto.StatusDTO;
import com.sugarcoat.uims.demo.application.service.RoleService;
import org.springframework.web.bind.annotation.*;

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
    public Result findOne(@NotBlank @PathVariable String id) {
        return null;
    }

    @GetMapping("page")
    public Result page(@RequestParam RolePageDTO pageDTO) {
        return null;
    }

    @PostMapping("save")
    public Result save(@RequestBody RoleDTO menuDTO) {
        return null;
    }

    @PostMapping("update")
    public Result update(@RequestBody RoleDTO menuDTO) {
        return null;
    }

    @PostMapping("status")
    public Result status(@RequestBody StatusDTO statusDTO) {
        return null;
    }
}
