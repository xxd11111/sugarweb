package com.xxd.sugarcoat.extend.uims.controller;

import com.xxd.sugarcoat.extend.uims.application.dto.MenuDTO;
import com.xxd.sugarcoat.extend.uims.application.dto.MenuPageDTO;
import com.xxd.sugarcoat.extend.uims.application.service.MenuService;
import com.xxd.sugarcoat.extend.uims.application.dto.StatusDTO;
import com.xxd.sugarcoat.support.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    private MenuService menuService;

    @GetMapping("{id}")
    public Result findOne(@NotBlank @PathVariable String id) {
        return null;
    }

    @GetMapping("page")
    public Result page(@RequestParam MenuPageDTO pageDTO) {
        return null;
    }

    @PostMapping("save")
    public Result save(@RequestBody MenuDTO menuDTO) {
        return null;
    }

    @PostMapping("update")
    public Result update(@RequestBody MenuDTO menuDTO) {
        return null;
    }

    @PostMapping("status")
    public Result status(@RequestBody StatusDTO statusDTO) {
        return null;
    }

}
