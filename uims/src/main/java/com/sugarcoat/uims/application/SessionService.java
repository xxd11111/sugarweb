package com.sugarcoat.uims.application;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.PasswordLoginDto;
import com.sugarcoat.uims.application.vo.LoginVo;
import com.sugarcoat.uims.domain.security.SessionInfo;

/**
 * 会话服务实现类
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/26
 */
public interface SessionService {

    LoginVo login(PasswordLoginDto passwordLoginDto);

    void authenticate();

    void logout();

    void kickOut(String sessionId);

    PageData<SessionInfo> page();

    SessionInfo find(String sessionId);

}
