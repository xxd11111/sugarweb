package com.xxd.sugarcoat.support.dev.user.utils;

import com.xxd.sugarcoat.support.dev.user.api.UserHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xxd
 * @version 1.0
 * @description: TODO
 * @date 2023/3/9
 */
@Component
public class AutoUserHandlerRegister {

    @Resource
    public void initUserHelper(UserHandler userHandler){
        UserUtil.userHandler = userHandler;
    }
}
