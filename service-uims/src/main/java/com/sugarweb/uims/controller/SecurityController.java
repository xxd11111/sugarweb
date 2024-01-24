package com.sugarweb.uims.controller;

import com.sugarweb.common.Result;
import com.sugarweb.security.TokenInfo;
import com.sugarweb.uims.application.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 安全控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final TokenService tokenService;

    @PostMapping("/kickOut")
    public Result<Void> kickOut(String accessToken) {
        tokenService.kickOut(accessToken);
        return Result.ok();
    }

    @PostMapping("/find")
    public Result<TokenInfo> find(String accessToken) {
        return Result.data(tokenService.find(accessToken));
    }

}
