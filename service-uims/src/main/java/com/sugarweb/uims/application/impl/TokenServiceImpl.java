package com.sugarweb.uims.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sugarweb.framework.common.HttpCode;
import com.sugarweb.framework.exception.SecurityException;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.framework.security.*;
import com.sugarweb.framework.utils.ServletUtil;
import com.sugarweb.uims.application.TokenService;
import com.sugarweb.uims.domain.dto.PasswordLoginDto;
import com.sugarweb.uims.domain.dto.TokenVo;
import com.sugarweb.uims.domain.SecurityLog;
import com.sugarweb.uims.domain.User;
import com.sugarweb.uims.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
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
        User user = Optional.ofNullable(userRepository.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, passwordLoginDto.getAccount())))
                .orElseThrow(() -> new ValidateException("not find user"));
        //密码校验
        user.checkCertificate(passwordLoginDto.getPassword());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireTime(LocalDateTime.now().plusSeconds(60*60*24*7));
        refreshToken.setUserId(user.getId());
        refreshToken.setIp(ServletUtil.getRequestIp());
        refreshToken.setUserAgent(ServletUtil.getUserAgent());

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(UUID.randomUUID().toString());
        accessToken.setRefreshToken(refreshToken.getRefreshToken());
        accessToken.setIp(refreshToken.getIp());
        accessToken.setUserAgent(refreshToken.getUserAgent());
        accessToken.setUserId(refreshToken.getUserId());

        accessTokenRepository.save(accessToken);

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
        tokenVo.setAccessToken(accessToken.getAccessToken());
        tokenVo.setAccessTokenExpiresTime(accessToken.getExpireTime());
        tokenVo.setRefreshToken(refreshToken.getRefreshToken());
        tokenVo.setRefreshTokenExpiresTime(refreshToken.getExpireTime());
        return tokenVo;
    }

    @Override
    public void logout() {
        String accessToken = SecurityHelper.getTokenInfo().getAccessToken();
        AccessToken accessTokenInfo = accessTokenRepository.findOne(accessToken);
        String refreshToken = accessTokenInfo.getRefreshToken();
        refreshTokenRepository.delete(refreshToken);
        accessTokenRepository.delete(accessToken);
    }

    @Override
    public void kickOut(String accessToken) {
        AccessToken accessTokenInfo = accessTokenRepository.findOne(accessToken);
        refreshTokenRepository.delete(accessTokenInfo.getRefreshToken());
        accessTokenRepository.delete(accessToken);
    }

    @Override
    public TokenVo refresh(String refreshToken) {
        if (refreshToken.isEmpty()) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        RefreshToken refreshTokenInfo = refreshTokenRepository.findOne(refreshToken);
        if (refreshTokenInfo == null) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        if (!Objects.equals(refreshTokenInfo.getIp(), ServletUtil.getRequestIp())) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        if (!Objects.equals(refreshTokenInfo.getUserAgent(), ServletUtil.getUserAgent())) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(UUID.randomUUID().toString());
        //set agent信息
        accessToken.setIp(refreshTokenInfo.getIp());
        accessToken.setUserAgent(refreshTokenInfo.getUserAgent());
        //set user信息
        accessToken.setUserId(refreshTokenInfo.getUserId());

        accessTokenRepository.save(accessToken);
        TokenVo tokenVo = new TokenVo();
        //登录信息 token等信息
        tokenVo.setUserId(refreshTokenInfo.getUserId());
        tokenVo.setAccessToken(accessToken.getAccessToken());
        tokenVo.setAccessTokenExpiresTime(accessToken.getExpireTime());
        tokenVo.setRefreshToken(refreshTokenInfo.getRefreshToken());
        tokenVo.setRefreshTokenExpiresTime(refreshTokenInfo.getExpireTime());
        return tokenVo;
    }
}
