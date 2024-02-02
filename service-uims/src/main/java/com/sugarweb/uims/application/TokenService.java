package com.sugarweb.uims.application;

import com.sugarweb.framework.security.AccessTokenInfo;
import com.sugarweb.uims.application.dto.PasswordLoginDto;
import com.sugarweb.uims.application.vo.TokenVo;

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
