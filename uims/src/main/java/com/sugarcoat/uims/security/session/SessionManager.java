package com.sugarcoat.uims.security.session;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.uims.security.SessionInfo;
import com.sugarcoat.uims.security.TokenInfo;

import java.util.List;

/**
 * 会话管理
 *
 * @author xxd
 * @since 2023/7/2 14:44
 */
public interface SessionManager {

    void create(SessionInfo sessionInfo);

    void update(SessionInfo sessionInfo);

    void delete(String userId, String sessionId);

    void deleteAll(String userId);

    SessionInfo authenticate(TokenInfo tokenInfo);

    PageData<SessionInfo> findAll(PageDto pageDto);

    SessionInfo findOne(String userId, String sessionId);

    List<SessionInfo> findAll(String userId);

}
