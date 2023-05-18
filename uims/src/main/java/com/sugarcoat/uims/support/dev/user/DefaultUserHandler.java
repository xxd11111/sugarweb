package com.sugarcoat.uims.support.dev.user;

import com.sugarcoat.uims.support.dev.user.api.UserInfo;
import com.sugarcoat.uims.support.dev.user.api.UserHandler;
import org.springframework.stereotype.Component;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/9
 */
@Component
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
