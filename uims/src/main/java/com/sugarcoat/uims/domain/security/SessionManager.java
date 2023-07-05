package com.sugarcoat.uims.domain.security;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;

import java.util.List;

/**
 * 会话管理
 *
 * @author xxd
 * @date 2023/7/2 14:44
 */
public interface SessionManager {

    void create(SessionInfo sessionInfo);

    void update(SessionInfo sessionInfo);

    void delete(String userId, String sessionId);

    void deleteAll(String userId);

    SessionInfo authenticate(TokenInfo tokenInfo);

    PageData<SessionInfo> findAll(PageDto pageDto);

    SessionInfo findOne(String sessionId);

    List<SessionInfo> findAll(String userId);

}
