package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.Result;
import com.sugarcoat.api.server.ApiTag;
import com.sugarcoat.uims.application.dto.PasswordLoginDTO;
import com.sugarcoat.uims.application.service.SessionService;
import com.sugarcoat.uims.application.dto.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticateController {

    private final SessionService sessionService;

    @ApiTag(code = "post:authenticate:login", name = "登录")
    @PostMapping("/login")
    public Result login(LoginVO loginVO) {
        PasswordLoginDTO login = sessionService.login(loginVO);
        return Result.data(login);
    }

    @ApiTag(code = "post:authenticate:logout", name = "退出")
    @PostMapping("/logout")
    public Result logout() {
        sessionService.logout();
        return Result.ok();
    }


}
