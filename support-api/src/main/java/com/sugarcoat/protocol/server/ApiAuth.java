package com.sugarcoat.protocol.server;

/**
 * TODO
 *
 * @author xxd
 * @since 2023/10/7 21:07
 */
public @interface ApiAuth {

    /**
     * 认证类型：匿名，无限制，已登录，授权，默认已登录
     */
    String authType() default "";

    String authCode() default "";

}
