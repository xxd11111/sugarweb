package com.sugarcoat.protocol.server;

/**
 * 服务接口标识注解
 *
 * @author xxd
 * @since 2023/6/27 22:40
 */
public @interface ApiTags {

    /**
     * 接口id 绑定以后勿变
     */
    String apiId();

    /**
     * 接口编码 一般取url
     */
    String code() default "";

    /**
     * 接口标签 为空时取apiId内容
     */
    String name() default "";

    /**
     * 接口描述 为空时取tags内容
     */
    String remark() default "";

    /**
     * 认证类型：匿名，无限制，已登录，授权，默认已登录
     */
    String authType() default "";

}
