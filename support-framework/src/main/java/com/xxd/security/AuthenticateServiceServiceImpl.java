package com.xxd.security;

import com.sugarcoat.protocol.common.HttpCode;
import com.sugarcoat.protocol.exception.SecurityException;
import com.sugarcoat.protocol.security.AuthenticateService;
import com.sugarcoat.protocol.security.TokenInfo;
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
