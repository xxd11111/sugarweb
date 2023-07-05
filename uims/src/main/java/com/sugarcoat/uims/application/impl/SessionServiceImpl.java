package com.sugarcoat.uims.application.impl;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.exception.ValidateException;
import com.sugarcoat.uims.application.SessionService;
import com.sugarcoat.uims.application.dto.PasswordLoginDto;
import com.sugarcoat.uims.application.vo.LoginVo;
import com.sugarcoat.uims.domain.security.SessionInfo;
import com.sugarcoat.uims.domain.security.SessionManager;
import com.sugarcoat.uims.domain.security.TokenInfo;
import com.sugarcoat.uims.domain.user.QUser;
import com.sugarcoat.uims.domain.user.User;
import com.sugarcoat.uims.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @date 2023/6/27 23:16
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @Override
    public LoginVo login(PasswordLoginDto passwordLoginDto) {
        User user = userRepository.findOne(QUser.user.username.eq(passwordLoginDto.getAccount()))
                .orElseThrow(() -> new ValidateException("not find user"));
        user.checkCertificate(passwordLoginDto.getPassword());
        SessionInfo sessionInfo = new SessionInfo();
        sessionManager.create(sessionInfo);
        LoginVo loginVo = new LoginVo();
        loginVo.setLastLoginIp("");
        loginVo.setLastLoginIp("");
        loginVo.setLastLoginIp("");
        loginVo.setLastLoginIp("");
        loginVo.setLastLoginIp("");
        return loginVo;
    }

    @Override
    public void authenticate(TokenInfo tokenInfo) {
        sessionManager.authenticate(tokenInfo);
    }

    @Override
    public void logout() {
        String userId = "";
        String sessionId = "";
        sessionManager.delete(userId, sessionId);
    }

    @Override
    public void kickOut(String userId, String sessionId) {
        sessionManager.delete(userId, sessionId);
    }

    @Override
    public PageData<SessionInfo> page() {
        return null;
    }

    @Override
    public SessionInfo find(String sessionId) {
        return null;
    }
}
