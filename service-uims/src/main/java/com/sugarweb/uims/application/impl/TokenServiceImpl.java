package com.sugarweb.uims.application.impl;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.framework.security.SecurityHelper;
import com.sugarweb.framework.security.TokenInfo;
import com.sugarweb.framework.security.UserInfo;
import com.sugarweb.framework.utils.ServletUtil;
import com.sugarweb.uims.application.TokenService;
import com.sugarweb.uims.application.dto.PasswordLoginDto;
import com.sugarweb.uims.application.vo.LoginVo;
import com.sugarweb.uims.domain.security.SecurityLog;
import com.sugarweb.uims.domain.user.QUser;
import com.sugarweb.uims.domain.user.User;
import com.sugarweb.uims.domain.user.UserRepository;
import com.sugarweb.framework.security.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    @Override
    public LoginVo login(PasswordLoginDto passwordLoginDto) {
        User user = userRepository.findOne(QUser.user.username.eq(passwordLoginDto.getAccount()))
                .orElseThrow(() -> new ValidateException("not find user"));
        //密码校验
        user.checkCertificate(passwordLoginDto.getPassword());

        TokenInfo tokenInfo = new TokenInfo();
        //set agent信息
        HttpServletRequest request = ServletUtil.getRequest();
        String requestIp = ServletUtil.getRequestIp();
        tokenInfo.setIp(requestIp);
        String userAgent = request.getHeader("User-Agent");
        tokenInfo.setUserAgent(userAgent);

        //set user信息
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        tokenInfo.setUserInfo(userInfo);

        //set token信息
        tokenInfo.setAccessToken(UUID.randomUUID().toString());
        tokenInfo.setAccessTokenExpireTime(LocalDateTime.now().plusSeconds(60*60*10));
        tokenInfo.setRefreshToken(UUID.randomUUID().toString());
        tokenInfo.setRefreshExpireTime(LocalDateTime.now().plusSeconds(60*60*24*7));

        tokenRepository.save(tokenInfo);

        //insert 登录日志
        SecurityLog securityLog = new SecurityLog();
        securityLog.setUserId(user.getId());
        securityLog.setActionIp(requestIp);
        securityLog.setUsername(user.getUsername());
        securityLog.setEventType("common");
        securityLog.setActionType("login");
        securityLog.setMessage("用户登录");

        //登录信息 token等信息
        LoginVo loginVo = new LoginVo();
        loginVo.setUserId(user.getId());
        loginVo.setAccessToken(tokenInfo.getAccessToken());
        loginVo.setAccessTokenExpiresTime(tokenInfo.getAccessTokenExpireTime());
        loginVo.setRefreshToken(tokenInfo.getRefreshToken());
        loginVo.setRefreshTokenExpiresTime(tokenInfo.getRefreshExpireTime());
        return loginVo;
    }

    @Override
    public void logout() {
        String accessToken = SecurityHelper.getTokenInfo().getAccessToken();
        tokenRepository.delete(accessToken);
        // refresh token
        // tokenRepository.delete(accessToken);
    }

    @Override
    public void kickOut(String accessToken) {
        tokenRepository.delete(accessToken);
    }

    @Override
    public PageData<TokenInfo> page() {
        return null;
    }

    @Override
    public TokenInfo find(String accessToken) {
        return tokenRepository.findOne(accessToken);
    }

    @Override
    public LoginVo refresh(String refreshToken) {
        TokenInfo tokenInfo = tokenRepository.findRefreshToken(refreshToken);
        tokenInfo.setAccessToken(UUID.randomUUID().toString());
        tokenInfo.setAccessTokenExpireTime(LocalDateTime.now().plusSeconds(60*60*10));

        LoginVo loginVo = new LoginVo();
        return loginVo;
    }
}
