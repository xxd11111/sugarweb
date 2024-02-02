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

    private final AccessTokenRepository accessTokenRepository;

    @Override
    public void authenticate(String accessToken) {
        if (accessToken.isEmpty()) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        AccessTokenInfo accessTokenInfo = accessTokenRepository.findOne(accessToken);
        if (accessTokenInfo == null) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        if (!Objects.equals(accessTokenInfo.getIp(), ServletUtil.getRequestIp())) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
        if (!Objects.equals(accessTokenInfo.getUserAgent(), ServletUtil.getUserAgent())) {
            throw new SecurityException(HttpCode.UNAUTHORIZED);
        }
    }

}
