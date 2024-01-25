package com.sugarweb.framework.security;

import com.sugarweb.framework.common.HttpCode;
import com.sugarweb.framework.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @version 1.0
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
