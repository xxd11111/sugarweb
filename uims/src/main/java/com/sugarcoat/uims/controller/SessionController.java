package com.sugarcoat.uims.controller;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.Result;
import com.sugarcoat.uims.application.SessionService;
import com.sugarcoat.uims.domain.security.SessionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会话控制器
 *
 * @author xxd
 * @date 2023/6/27 22:11
 */
@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/kickOut")
    public Result<Void> kickOut(String userId, String sessionId) {
        sessionService.kickOut(userId, sessionId);
        return Result.ok();
    }

    @PostMapping("/find")
    public Result<SessionInfo> find(String sessionId) {
        return Result.data(sessionService.find(sessionId));
    }

    @PostMapping("/page")
    public Result<PageData<SessionInfo>> sessionPage() {
        return Result.data(sessionService.page());
    }

}
