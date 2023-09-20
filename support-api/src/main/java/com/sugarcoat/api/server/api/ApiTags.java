package com.sugarcoat.api.server.api;

/**
 * 服务接口标识注解
 *
 * @author xxd
 * @since 2023/6/27 22:40
 */
public @interface ApiTags {

    /**
     * 接口id
     */
    String apiId();

    /**
     * 接口标签 为空时取apiId内容
     */
    String tags() default "";

    /**
     * 接口描述 为空时取tags内容
     */
    String title() default "";

    /**
     * 认证类型：匿名，无限制，已登录，授权，默认已登录
     */
    String authType() default "";

}
