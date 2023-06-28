package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.PasswordLoginDTO;
import com.sugarcoat.uims.application.dto.LoginVO;
import com.sugarcoat.uims.domain.security.SessionInfo;
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

    @Override
    public PasswordLoginDTO login(LoginVO loginVO) {
        return null;
    }

    @Override
    public void authenticate() {

    }

    @Override
    public void logout() {

    }

    @Override
    public void kickOut(String sessionId) {

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
