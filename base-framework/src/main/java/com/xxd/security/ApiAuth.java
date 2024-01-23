package com.xxd.security;

import java.lang.annotation.*;

/**
 * api权限注解
 *
 * @author xxd
 * @since 2023/10/7 21:07
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiAuth {

    /**
     * 认证类型：匿名，无限制，已登录，授权，默认已登录
     */
    String authType() default "";

    /**
     * 权限编码
     */
    String[] authCode() default {};

}
