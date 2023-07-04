package com.sugarcoat.uims.domain.security;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import com.sugarcoat.uims.domain.user.User;

/**
 * 会话管理
 *
 * @author xxd
 * @date 2023/7/2 14:44
 */
public interface SessionManager {

    SessionInfo create(User user);

    SessionInfo refresh();

    void delete(String sessionId);

    SessionInfo authenticate();

    PageData<SessionInfo> findAll(PageParameter pageParameter);

    SessionInfo findOne(String sessionId);

}
