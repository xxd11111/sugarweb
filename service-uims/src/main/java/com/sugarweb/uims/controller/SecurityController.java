package com.sugarweb.uims.controller;

import com.sugarweb.framework.common.R;
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
@RequestMapping("/security/manage")
@RequiredArgsConstructor
public class SecurityController {

    private final TokenService tokenService;

    @PostMapping("/kickOut")
    public R<Void> kickOut(String accessToken) {
        tokenService.kickOut(accessToken);
        return R.ok();
    }

}
