package com.xxd.sugarcoat.extend.uims.controller;

import com.xxd.sugarcoat.extend.uims.application.dto.StatusDTO;
import com.xxd.sugarcoat.extend.uims.application.dto.UserDTO;
import com.xxd.sugarcoat.extend.uims.application.dto.UserPageDTO;
import com.xxd.sugarcoat.extend.uims.application.service.UserService;
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
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @GetMapping("{id}")
    public R findOne(@NotBlank @PathVariable String id) {
        return null;
    }

    @GetMapping("page")
    public R page(@RequestParam UserPageDTO pageDTO) {
        return null;
    }

    @PostMapping("save")
    public R save(@RequestBody UserDTO userDTO) {
        return null;
    }

    @PostMapping("update")
    public R update(@RequestBody UserDTO userDTO) {
        return null;
    }

    @PostMapping("status")
    public R status(@RequestBody StatusDTO statusDTO) {
        return null;
    }
}
