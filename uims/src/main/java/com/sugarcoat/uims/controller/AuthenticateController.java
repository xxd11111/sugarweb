package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.Result;
import com.sugarcoat.api.server.ServerApi;
import com.sugarcoat.uims.application.dto.LoginVO;
import com.sugarcoat.uims.application.service.SessionService;
import com.sugarcoat.uims.domain.security.LoginDTO;
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

    @ServerApi(code = "post:authenticate:login", name = "登录")
    @PostMapping("/login")
    public Result login(LoginDTO loginDTO) {
        LoginVO login = sessionService.login(loginDTO);
        return Result.data(login);
    }

    @ServerApi(code = "post:authenticate:logout", name = "退出")
    @PostMapping("/logout")
    public Result logout() {
        sessionService.logout();
        return Result.ok();
    }


}
