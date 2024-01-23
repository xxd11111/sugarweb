package com.xxd.security;

import com.xxd.common.HttpCode;
import com.xxd.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @since 2023/6/27 23:16
 */
@Service
@RequiredArgsConstructor
public class AuthenticateServiceServiceImpl implements AuthenticateService {

    private final TokenRepository tokenRepository;

    @Override
    public TokenInfo authenticate(String accessToken) {
        TokenInfo tokenInfo = tokenRepository.findOne(accessToken);
        if (tokenInfo == null) {
            throw new SecurityException("");
        }
        boolean flag = true;
        //todo check ip mac
        if (!flag) {
            // todo send securityLog
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        return tokenInfo;
    }

}
