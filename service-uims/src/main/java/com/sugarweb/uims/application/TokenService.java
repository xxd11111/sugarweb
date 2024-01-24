package com.sugarweb.uims.application;

import com.sugarweb.common.PageData;
import com.sugarweb.security.TokenInfo;
import com.sugarweb.uims.application.dto.PasswordLoginDto;
import com.sugarweb.uims.application.vo.LoginVo;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @version 1.0
 */
public interface TokenService{

    LoginVo login(PasswordLoginDto passwordLoginDto);

    void logout();

    void kickOut(String sessionId);

    PageData<TokenInfo> page();

    TokenInfo find(String sessionId);

    LoginVo refresh(String refreshToken);

}
