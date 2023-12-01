package com.sugarcoat.uims.controller;

import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.uims.application.dto.NewPasswordDto;
import com.sugarcoat.uims.application.dto.UserDto;
import com.sugarcoat.uims.application.dto.UserQueryDto;
import com.sugarcoat.uims.application.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author xxd
 * @since 2022-12-28
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
    public Result page(@RequestParam UserQueryDto userQueryDTO) {
        return Result.data(userService.page(userQueryDTO));
    }

    @PostMapping("save")
    public Result save(@RequestBody UserDto userDTO) {
        return Result.data(userService.save(userDTO));
    }

    @PostMapping("modifyPassword")
    public Result save(@RequestBody NewPasswordDto newPasswordDTO) {
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

    @GetMapping("existMobilePhone")
    public Result existMobilePhone(String mobilePhone) {
        return Result.data(userService.existMobilePhone(mobilePhone));
    }
}
