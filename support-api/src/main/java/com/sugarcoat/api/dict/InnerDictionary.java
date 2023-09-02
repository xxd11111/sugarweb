package com.sugarcoat.api.dict;

import java.lang.annotation.*;

/**
 * 只用于开发阶段，优化字典变动时，自动维护字典项
 * 该部分属于程序内字典，不允许外部编辑，优先级最高
 *
 * 枚举字典用于初始化
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/21
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerDictionary {

	/**
	 * 字典编码 为空时使用字段值
	 */
	String code() default "";

	/**
	 * 字典名称 为空时使用类名
	 */
	String name() default "";

}
