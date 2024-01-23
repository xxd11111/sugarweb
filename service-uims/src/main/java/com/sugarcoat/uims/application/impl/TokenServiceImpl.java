package com.sugarcoat.uims.application.impl;

import com.xxd.common.PageData;
import com.xxd.exception.ValidateException;
import com.xxd.security.SecurityHelper;
import com.xxd.security.TokenInfo;
import com.sugarcoat.uims.application.TokenService;
import com.sugarcoat.uims.application.dto.PasswordLoginDto;
import com.sugarcoat.uims.application.vo.LoginVo;
import com.sugarcoat.uims.domain.user.QUser;
import com.sugarcoat.uims.domain.user.User;
import com.sugarcoat.uims.domain.user.UserRepository;
import com.xxd.security.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @since 2023/6/27 23:16
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
        user.checkCertificate(passwordLoginDto.getPassword());
        TokenInfo tokenInfo = new TokenInfo();

        //set agent信息
        // tokenInfo.setIp();
        // tokenInfo.setMac();
        // tokenInfo.setPlatform();
        // tokenInfo.setUserAgent();
        tokenInfo.setUserId(user.getId());

        //set token信息
        tokenInfo.setAccessToken(UUID.randomUUID().toString());
        tokenInfo.setExpireTime(LocalDateTime.now().plusSeconds(60*60*10));
        tokenInfo.setRefreshToken(UUID.randomUUID().toString());
        tokenInfo.setRefreshExpireTime(LocalDateTime.now().plusSeconds(60*60*24*7));

        tokenRepository.save(tokenInfo);

        //todo 用户信息 菜单按钮
        LoginVo loginVo = new LoginVo();
        loginVo.setUserId(user.getId());
        loginVo.setAccountType(user.getAccountType());
        loginVo.setEmail(user.getEmail());
        loginVo.setMobilePhone(user.getMobilePhone());
        loginVo.setUsername(user.getUsername());
        loginVo.setNickName(user.getNickName());

        //todo insert 登录日志

        return loginVo;
    }

    @Override
    public void logout() {
        String userId = SecurityHelper.getUserInfo().getId();
        String accessToken = "SecurityHelper.getUserInfo()";
        tokenRepository.delete(accessToken);
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
        tokenInfo.setExpireTime(LocalDateTime.now().plusSeconds(60*60*10));

        LoginVo loginVo = new LoginVo();
        return loginVo;
    }
}
