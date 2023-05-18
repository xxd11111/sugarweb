package com.sugarcoat.uims.support.dev.user.api;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/9
 */
public interface UserHandler {

    UserInfo currentUser();

    void loadUser(UserInfo userInfo);

    void init();

    void destroy();
}
