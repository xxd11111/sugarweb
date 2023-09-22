package com.sugarcoat.protocol.data;

/**
 * todo 敏感词
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
public @interface SensitiveWord {

	String type() default "";

}
