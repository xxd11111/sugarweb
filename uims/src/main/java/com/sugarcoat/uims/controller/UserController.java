package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.StatusDTO;
import com.sugarcoat.uims.application.dto.UserDTO;
import com.sugarcoat.uims.application.dto.UserPageDTO;
import com.sugarcoat.uims.application.service.UserQueryVO;
import com.sugarcoat.uims.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

/**
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
	public Result page(@RequestParam UserQueryVO userQueryVO) {
		return Result.data(userService.page(userQueryVO));

	}

	@PostMapping("save")
	public Result save(@RequestBody UserDTO userDTO) {
		return Result.data(userService.save(userDTO));

	}

}
