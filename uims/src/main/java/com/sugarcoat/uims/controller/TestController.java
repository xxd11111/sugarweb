package com.sugarcoat.uims.controller;


import com.sugarcoat.api.common.Result;
import com.sugarcoat.api.protection.Idempotent;
import com.sugarcoat.uims.application.vo.EmailLoginVo;
import com.sugarcoat.uims.application.vo.RoleVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @Idempotent(expire = 2L, unit = TimeUnit.DAYS, errorMsg = "错误的请求")
    @PostMapping("add")
    public Result<String> findOne(@RequestBody EmailLoginVo vo) {
        return Result.data("你好:" + vo.toString());
    }

}
