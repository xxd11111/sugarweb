package com.xxd.sugarcoat.support.user;

import com.xxd.sugarcoat.support.user.api.UserInfo;
import com.xxd.sugarcoat.support.user.api.UserHandler;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/9
 */
public class DefaultUserHandler implements UserHandler {

    private final ThreadLocal<UserInfo> CURRENT_ACCOUNT = new ThreadLocal<>();

    @Override
    public UserInfo currentUser() {
        return CURRENT_ACCOUNT.get();
    }

    @Override
    public void loadUser(UserInfo userInfo) {
        CURRENT_ACCOUNT.set(userInfo);
    }

    @Override
    public void destroy() {
        CURRENT_ACCOUNT.remove();
    }

    @Override
    public void init() {
        
    }
}
