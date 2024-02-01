package com.sugarweb.framework.security;

import com.sugarweb.framework.common.HttpCode;
import com.sugarweb.framework.exception.SecurityException;
import com.sugarweb.framework.utils.ServletUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    public void authenticate(String accessToken) {
        if (accessToken.isEmpty()) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        TokenInfo tokenInfo = tokenRepository.findOne(accessToken);
        if (tokenInfo == null) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        if (!Objects.equals(tokenInfo.getIp(), ServletUtil.getRequestIp())) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        if (!Objects.equals(tokenInfo.getUserAgent(), ServletUtil.getUserAgent())) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
    }

}
