package com.sugarcoat.api.orm;

/**
 * todo 加密存储注解，存储时加密，获取时解密或不解密
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/1
 */
public @interface DbEncrypt {

	String encryptType() default "";

	boolean decrypt() default false;

}
