package com.sugarweb.uims.controller;

import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.uims.dto.*;
import com.sugarweb.uims.service.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserManageController {

    private final UserService userService;

    @GetMapping("detail")
    public R<UserDetailDto> detail(@NotBlank String id) {
        return R.data(userService.detail(id));
    }

    @GetMapping("list")
    public R page(PageQuery pageQuery, UserQuery userQuery) {
        return R.data(userService.page(pageQuery, userQuery));
    }

    @PostMapping("save")
    public R save(@RequestBody UserSaveDto dto) {
        return R.data(userService.save(dto));
    }

    @PostMapping("update")
    public R update(@RequestBody UserUpdateDto dto) {
        return R.data(userService.update(dto));
    }

    @PostMapping("updatePassword")
    public R updatePassword(@RequestBody NewPasswordDto newPasswordDTO) {
        userService.modifyPassword(newPasswordDTO);
        return R.ok();
    }

	@PostMapping("remove")
	public R<Void> remove(String id) {
		userService.remove(id);
		return R.ok();
	}

    @GetMapping("existEmail")
    public R existEmail(String email) {
        return R.data(userService.existEmail(email));
    }

    @GetMapping("existUsername")
    public R existUsername(String username) {
        return R.data(userService.existUsername(username));
    }

    @GetMapping("existMobilePhone")
    public R existMobilePhone(String mobilePhone) {
        return R.data(userService.existMobilePhone(mobilePhone));
    }
}
