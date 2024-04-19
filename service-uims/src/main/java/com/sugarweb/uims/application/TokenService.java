package com.sugarweb.uims.application;

import com.sugarweb.uims.domain.dto.PasswordLoginDto;
import com.sugarweb.uims.domain.dto.TokenVo;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @version 1.0
 */
public interface TokenService{

    TokenVo login(PasswordLoginDto passwordLoginDto);

    void logout();

    void kickOut(String sessionId);

    TokenVo refresh(String refreshToken);

}
