package com.sugarcoat.uims.controller;

import com.sugarcoat.protocol.common.Result;
import com.sugarcoat.uims.application.dto.PasswordLoginDto;
import com.sugarcoat.uims.application.SessionService;
import com.sugarcoat.uims.application.vo.LoginVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/26
 */
@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticateController {

    private final SessionService sessionService;

    @PostMapping("/login")
    public Result<LoginVo> login(PasswordLoginDto passwordLoginDto) {
        LoginVo login = sessionService.login(passwordLoginDto);
        return Result.data(login);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        sessionService.logout();
        return Result.ok();
    }


}
