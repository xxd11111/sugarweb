package com.xxd.sugarcoat.extend.uims.controller;

import com.xxd.sugarcoat.extend.uims.application.dto.ApiPageDTO;
import com.xxd.sugarcoat.extend.uims.application.dto.StatusDTO;
import com.xxd.sugarcoat.extend.uims.application.service.ApiService;
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
@RequestMapping("/api")
public class ApiController {
    private ApiService apiService;

    @GetMapping("{id}")
    public R findOne(@NotBlank @PathVariable String id) {
        return null;
    }

    @GetMapping("page")
    public R page(@RequestParam ApiPageDTO pageDTO) {
        return null;
    }

    @PostMapping("status")
    public R status(@RequestBody StatusDTO statusDTO) {
        return null;
    }

}
