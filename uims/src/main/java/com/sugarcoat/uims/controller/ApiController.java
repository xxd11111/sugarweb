package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.ApiPageDTO;
import com.sugarcoat.uims.application.dto.StatusDTO;
import com.sugarcoat.uims.application.service.ApiService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/api")
public class ApiController {

	private ApiService apiService;

	@GetMapping("{id}")
	public Result findOne(@NotBlank @PathVariable String id) {
		return null;
	}

	@GetMapping("page")
	public Result page(@RequestParam ApiPageDTO pageDTO) {
		return null;
	}

	@PostMapping("status")
	public Result status(@RequestBody StatusDTO statusDTO) {
		return null;
	}

}
