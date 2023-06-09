package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.MenuDTO;
import com.sugarcoat.uims.application.dto.MenuPageDTO;
import com.sugarcoat.uims.application.service.MenuService;
import com.sugarcoat.uims.application.dto.StatusDTO;
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
