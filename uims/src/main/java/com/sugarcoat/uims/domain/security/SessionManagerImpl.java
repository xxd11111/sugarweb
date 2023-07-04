package com.sugarcoat.uims.domain.security;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import com.sugarcoat.uims.domain.user.User;
import org.springframework.stereotype.Component;

/**
 * 会话管理实现类
 *
 * @author xxd
 * @date 2023/7/2 20:51
 */
@Component
public class SessionManagerImpl implements SessionManager {

    @Override
    public SessionInfo create(User user) {
        return null;
    }

    @Override
    public SessionInfo refresh() {
        return null;
    }

    @Override
    public void delete(String sessionId) {

    }

    @Override
    public SessionInfo authenticate() {
        return null;
    }

    @Override
    public PageData<SessionInfo> findAll(PageParameter pageParameter) {
        return null;
    }

    @Override
    public SessionInfo findOne(String sessionId) {
        return null;
    }
}
