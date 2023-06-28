package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.NewPasswordDTO;
import com.sugarcoat.uims.application.dto.UserDTO;
import com.sugarcoat.uims.application.dto.UserQueryDTO;
import com.sugarcoat.uims.application.service.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author xxd
 * @description TODO
 * @date 2022-12-28
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public Result findOne(@NotBlank @PathVariable String id) {
        return Result.data(userService.find(id));
    }

    @GetMapping("page")
    public Result page(@RequestParam UserQueryDTO userQueryDTO) {
        return Result.data(userService.page(userQueryDTO));
    }

    @PostMapping("save")
    public Result save(@RequestBody UserDTO userDTO) {
        return Result.data(userService.save(userDTO));
    }

    @PostMapping("modifyPassword")
    public Result save(@RequestBody NewPasswordDTO newPasswordDTO) {
        userService.modifyPassword(newPasswordDTO);
        return Result.ok();
    }

	@PostMapping("remove")
	public Result<Void> remove(String id) {
		userService.remove(id);
		return Result.ok();
	}

    @GetMapping("existEmail")
    public Result existEmail(String email) {
        return Result.data(userService.existEmail(email));
    }

    @GetMapping("existUsername")
    public Result existUsername(String username) {
        return Result.data(userService.existUsername(username));
    }

    @GetMapping("existPhoneNumber")
    public Result existPhoneNumber(String phoneNumber) {
        return Result.data(userService.existPhoneNumber(phoneNumber));
    }
}
