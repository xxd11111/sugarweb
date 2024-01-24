package com.sugarcoat.uims.application;

import com.xxd.common.PageData;
import com.xxd.security.TokenInfo;
import com.sugarcoat.uims.application.dto.PasswordLoginDto;
import com.sugarcoat.uims.application.vo.LoginVo;

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
