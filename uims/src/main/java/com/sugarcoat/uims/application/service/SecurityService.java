package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.LoginDTO;
import com.sugarcoat.uims.domain.security.LoginVO;
import com.sugarcoat.uims.domain.security.SessionInfo;

/**
 * 安全服务
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
public interface SecurityService {

    LoginDTO login(LoginVO loginInfo);

    void authenticate();

    void logout();

    void logout(String sessionId);

    PageData<SessionInfo> sessionPage();

    SessionInfo find(String sessionId);

}
