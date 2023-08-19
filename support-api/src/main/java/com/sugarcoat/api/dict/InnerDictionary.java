package com.sugarcoat.api.dict;

import java.lang.annotation.*;

/**
 * 枚举字典用于初始化
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/21
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerDictionary {

	String groupCode() default "";

	String groupName() default "";

}
