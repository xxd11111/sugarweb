package com.sugarweb.uims.application.impl;

import com.sugarweb.framework.common.HttpCode;
import com.sugarweb.framework.exception.SecurityException;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.framework.security.*;
import com.sugarweb.framework.utils.ServletUtil;
import com.sugarweb.uims.application.TokenService;
import com.sugarweb.uims.application.dto.PasswordLoginDto;
import com.sugarweb.uims.application.vo.TokenVo;
import com.sugarweb.uims.domain.security.SecurityLog;
import com.sugarweb.uims.domain.user.QUser;
import com.sugarweb.uims.domain.user.User;
import com.sugarweb.uims.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
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

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public TokenVo login(PasswordLoginDto passwordLoginDto) {
        User user = userRepository.findOne(QUser.user.username.eq(passwordLoginDto.getAccount()))
                .orElseThrow(() -> new ValidateException("not find user"));
        //密码校验
        user.checkCertificate(passwordLoginDto.getPassword());

        RefreshTokenInfo refreshTokenInfo = new RefreshTokenInfo();
        refreshTokenInfo.setRefreshToken(UUID.randomUUID().toString());
        refreshTokenInfo.setExpireTime(LocalDateTime.now().plusSeconds(60*60*24*7));
        refreshTokenInfo.setUserId(user.getId());
        refreshTokenInfo.setIp(ServletUtil.getRequestIp());
        refreshTokenInfo.setUserAgent(ServletUtil.getUserAgent());

        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setAccessToken(UUID.randomUUID().toString());
        accessTokenInfo.setRefreshToken(refreshTokenInfo.getRefreshToken());
        accessTokenInfo.setIp(refreshTokenInfo.getIp());
        accessTokenInfo.setUserAgent(refreshTokenInfo.getUserAgent());
        accessTokenInfo.setUserId(refreshTokenInfo.getUserId());

        accessTokenRepository.save(accessTokenInfo);

        //insert 登录日志
        SecurityLog securityLog = new SecurityLog();
        securityLog.setUserId(user.getId());
        securityLog.setActionIp(ServletUtil.getRequestIp());
        securityLog.setUsername(user.getUsername());
        securityLog.setEventType("common");
        securityLog.setActionType("login");
        securityLog.setMessage("用户登录");

        //登录信息 token等信息
        TokenVo tokenVo = new TokenVo();
        tokenVo.setUserId(user.getId());
        tokenVo.setAccessToken(accessTokenInfo.getAccessToken());
        tokenVo.setAccessTokenExpiresTime(accessTokenInfo.getExpireTime());
        tokenVo.setRefreshToken(refreshTokenInfo.getRefreshToken());
        tokenVo.setRefreshTokenExpiresTime(refreshTokenInfo.getExpireTime());
        return tokenVo;
    }

    @Override
    public void logout() {
        String accessToken = SecurityHelper.getTokenInfo().getAccessToken();
        AccessTokenInfo accessTokenInfo = accessTokenRepository.findOne(accessToken);
        String refreshToken = accessTokenInfo.getRefreshToken();
        refreshTokenRepository.delete(refreshToken);
        accessTokenRepository.delete(accessToken);
    }

    @Override
    public void kickOut(String accessToken) {
        AccessTokenInfo accessTokenInfo = accessTokenRepository.findOne(accessToken);
        refreshTokenRepository.delete(accessTokenInfo.getRefreshToken());
        accessTokenRepository.delete(accessToken);
    }

    @Override
    public TokenVo refresh(String refreshToken) {
        if (refreshToken.isEmpty()) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        RefreshTokenInfo refreshTokenInfo = refreshTokenRepository.findOne(refreshToken);
        if (refreshTokenInfo == null) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        if (!Objects.equals(refreshTokenInfo.getIp(), ServletUtil.getRequestIp())) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        if (!Objects.equals(refreshTokenInfo.getUserAgent(), ServletUtil.getUserAgent())) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }

        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setAccessToken(UUID.randomUUID().toString());
        //set agent信息
        accessTokenInfo.setIp(refreshTokenInfo.getIp());
        accessTokenInfo.setUserAgent(refreshTokenInfo.getUserAgent());
        //set user信息
        accessTokenInfo.setUserId(refreshTokenInfo.getUserId());

        accessTokenRepository.save(accessTokenInfo);
        TokenVo tokenVo = new TokenVo();
        //登录信息 token等信息
        tokenVo.setUserId(refreshTokenInfo.getUserId());
        tokenVo.setAccessToken(accessTokenInfo.getAccessToken());
        tokenVo.setAccessTokenExpiresTime(accessTokenInfo.getExpireTime());
        tokenVo.setRefreshToken(refreshTokenInfo.getRefreshToken());
        tokenVo.setRefreshTokenExpiresTime(refreshTokenInfo.getExpireTime());
        return tokenVo;
    }
}
