package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.StatusDTO;
import com.sugarcoat.uims.application.dto.UserDTO;
import com.sugarcoat.uims.application.dto.UserPageDTO;
import com.sugarcoat.uims.application.service.UserService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

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
	public Result findOne(@NotBlank @PathVariable String id) {
		return null;
	}

	@GetMapping("page")
	public Result page(@RequestParam UserPageDTO pageDTO) {
		return null;
	}

	@PostMapping("save")
	public Result save(@RequestBody UserDTO userDTO) {
		return null;
	}

	@PostMapping("update")
	public Result update(@RequestBody UserDTO userDTO) {
		return null;
	}

	@PostMapping("status")
	public Result status(@RequestBody StatusDTO statusDTO) {
		return null;
	}

}
