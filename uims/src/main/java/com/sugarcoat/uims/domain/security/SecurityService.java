package com.sugarcoat.uims.domain.security;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.user.UserInfo;

/**
 * 安全服务
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
public interface SecurityService {

    UserInfo login(LoginVO loginInfo);

    void logout();

    void logout(String sessionId);

    PageData<SessionInfo> sessionPage();

    SessionInfo find(String sessionId);

}
