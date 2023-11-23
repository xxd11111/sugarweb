package com.sugarcoat.protocol.dictionary;

import java.lang.annotation.*;

/**
 * 字典变动时，自动维护字典项
 * 该部分属于程序内字典，不允许外部编辑，优先级最高
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/21
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictionaryValue {

    /**
     * 字典编码 为空时使用字段值
     */
    String value() default "";


}
