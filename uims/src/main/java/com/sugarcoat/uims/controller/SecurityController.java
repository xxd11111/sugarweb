package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.dto.LoginDTO;
import com.sugarcoat.uims.application.service.SecurityService;
import com.sugarcoat.uims.domain.security.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
@RestController
//@RequestMapping("/")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/login")
    public Result login(LoginVO loginInfo) {
        LoginDTO login = securityService.login(loginInfo);
        return Result.data(login);
    }

    @PostMapping("/logout")
    public Result logout() {
        securityService.logout();
        return Result.ok();
    }

    @PostMapping("/kickOut")
    public Result kickOut(String sessionId) {
        securityService.kickOut(sessionId);
        return Result.ok();
    }

    @PostMapping("/find")
    public Result find(String sessionId) {
        return Result.data(securityService.find(sessionId));
    }

    @PostMapping("/sessionPage")
    public Result sessionPage() {
        return Result.data(securityService.sessionPage());
    }

}
