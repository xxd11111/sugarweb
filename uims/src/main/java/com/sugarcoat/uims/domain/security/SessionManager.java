package com.sugarcoat.uims.domain.security;

/**
 * 会话管理
 *
 * @author xxd
 * @date 2023/7/2 14:44
 */
public interface SessionManager {

    String create();

    String refresh();

    String delete();

    String authenticate();

}
