package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.MenuDTO;
import com.sugarcoat.uims.application.dto.MenuPageDTO;
import com.sugarcoat.uims.application.dto.MenuQueryVO;
import com.sugarcoat.uims.application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

/**
 * 菜单控制器
 *
 * @author xxd
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping("{id}")
	public Result<MenuDTO> findOne(@NotBlank @PathVariable String id) {
		return Result.data(menuService.find(id));
	}

	@GetMapping("page")
	public Result<PageData<MenuPageDTO>> page(@RequestParam MenuQueryVO menuQueryVO) {
		return Result.data(menuService.page(menuQueryVO));
	}

	@PostMapping("save")
	public Result<String> save(@RequestBody MenuDTO menuDTO) {
		return Result.data(menuService.save(menuDTO));
	}

	@PostMapping("update")
	public Result<Void> update(@RequestBody MenuDTO menuDTO) {
		menuService.modify(menuDTO);
		return Result.ok();
	}

}
