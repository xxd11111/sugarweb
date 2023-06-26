package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.*;
import com.sugarcoat.uims.application.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

/**
 * 角色控制器
 *
 * @author xxd
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

	private final RoleService roleService;

	@GetMapping("{id}")
	public Result<RoleInfoVO> findOne(@NotBlank @PathVariable String id) {
		return Result.data(roleService.find(id));
	}

	@GetMapping("page")
	public Result<PageData<RolePageDTO>> page(@RequestParam RoleQueryVO roleQueryVO) {
		return Result.data(roleService.page(roleQueryVO));
	}

	@PostMapping("save")
	public Result<String> save(@RequestBody RoleDTO menuDTO) {
		return Result.data(roleService.save(menuDTO));
	}

	@PostMapping("update")
	public Result<Void> update(@RequestBody RoleDTO menuDTO) {
		roleService.modify(menuDTO);
		return Result.ok();

	}

}
