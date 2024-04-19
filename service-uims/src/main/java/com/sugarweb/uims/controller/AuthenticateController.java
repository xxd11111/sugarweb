package com.sugarweb.uims.controller;

import com.sugarweb.framework.common.R;
import com.sugarweb.uims.domain.dto.PasswordLoginDto;
import com.sugarweb.uims.domain.dto.TokenVo;
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
    public R<TokenVo> login(PasswordLoginDto passwordLoginDto) {
        TokenVo login = tokenService.login(passwordLoginDto);
        return R.data(login);
    }

    @PostMapping("/logout")
    @Operation(summary = "登出")
    public R<Void> logout() {
        tokenService.logout();
        return R.ok();
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新token")
    public R<TokenVo> refresh(String refreshToken) {
        TokenVo login = tokenService.refresh(refreshToken);
        return R.data(login);
    }

}
