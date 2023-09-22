package com.sugarcoat.uims.controller;


import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.protocol.protection.Idempotent;
import com.sugarcoat.uims.application.vo.EmailLoginVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @Idempotent(errorMsg = "错误的请求")
    @PostMapping("add")
    public Result<String> findOne(@RequestBody EmailLoginVo vo) {
        return Result.data("你好:" + vo.toString());
    }

}
