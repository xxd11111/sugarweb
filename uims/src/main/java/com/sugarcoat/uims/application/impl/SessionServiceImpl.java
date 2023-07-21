package com.sugarcoat.uims.application.impl;

import cn.hutool.core.util.IdUtil;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.exception.ValidateException;
import com.sugarcoat.uims.application.SessionService;
import com.sugarcoat.uims.application.dto.PasswordLoginDto;
import com.sugarcoat.uims.application.vo.LoginVo;
import com.sugarcoat.uims.domain.menu.Menu;
import com.sugarcoat.uims.domain.security.SecurityHelper;
import com.sugarcoat.uims.domain.security.SessionInfo;
import com.sugarcoat.uims.domain.security.SessionManager;
import com.sugarcoat.uims.domain.user.QUser;
import com.sugarcoat.uims.domain.user.User;
import com.sugarcoat.uims.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        sessionInfo.setSessionId(IdUtil.simpleUUID());
        sessionInfo.setUserId(user.getId());
        //todo
        //sessionInfo.setIp();
        //sessionInfo.setMac();
        //sessionInfo.setPlatform();
        //sessionInfo.setUserAgent();
        long loginTime = System.currentTimeMillis();
        sessionInfo.setLoginTime(loginTime);
        sessionInfo.setLastRefreshTime(loginTime);
        //todo
        //sessionInfo.setMaxActiveTime(1000 * 60 * 60 * 24L);
        sessionManager.create(sessionInfo);
        LoginVo loginVo = new LoginVo();
        loginVo.setUserId(user.getId());
        loginVo.setAccountType(user.getAccountType());
        loginVo.setEmail(user.getEmail());
        loginVo.setMobilePhone(user.getMobilePhone());
        loginVo.setUsername(user.getUsername());
        loginVo.setNickName(user.getNickName());
        Set<Menu> menus = user.listMenus();
        //todo
        //loginVo.setMenus(menus);

        loginVo.setSessionId(sessionInfo.getSessionId());
        //loginVo.setLastLoginTime();
        //loginVo.setLastLoginPlatform("");
        //loginVo.setLastLoginIp("");

        //todo insert loginLog

        return loginVo;
    }

    @Override
    public void logout() {
        String userId = SecurityHelper.currentUserId();
        String sessionId = SecurityHelper.currentSessionId();
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
    public SessionInfo find(String userId, String sessionId) {
        return sessionManager.findOne(userId, sessionId);
    }
}
