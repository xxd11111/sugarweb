package com.sugarweb.dict.auto;

import java.lang.annotation.*;

/**
 * 字典变动时，自动维护字典项
 * 该部分属于程序内字典，不允许外部编辑和增删（只允许更改中文名称）
 *
 * @author xxd
 * @version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictionaryItem {

    String value() default "";

    String code() default "";

}
