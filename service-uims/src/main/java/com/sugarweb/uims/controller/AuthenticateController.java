package com.sugarweb.uims.controller;

import com.sugarweb.framework.common.Result;
import com.sugarweb.uims.application.dto.PasswordLoginDto;
import com.sugarweb.uims.application.vo.TokenVo;
import com.sugarweb.uims.application.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticateController {

    private final TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<TokenVo> login(PasswordLoginDto passwordLoginDto) {
        TokenVo login = tokenService.login(passwordLoginDto);
        return Result.data(login);
    }

    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<Void> logout() {
        tokenService.logout();
        return Result.ok();
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新token")
    public Result<TokenVo> refresh(String refreshToken) {
        TokenVo login = tokenService.refresh(refreshToken);
        return Result.data(login);
    }

}
