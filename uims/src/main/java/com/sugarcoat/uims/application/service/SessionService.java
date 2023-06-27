package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.LoginVO;
import com.sugarcoat.uims.domain.security.LoginDTO;
import com.sugarcoat.uims.domain.security.SessionInfo;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
public interface SessionService {

    LoginVO login(LoginDTO loginDTO);

    void authenticate();

    void logout();

    void kickOut(String sessionId);

    PageData<SessionInfo> page();

    SessionInfo find(String sessionId);

}
