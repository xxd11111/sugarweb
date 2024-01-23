package com.sugarcoat.support.dict.api;

import java.lang.annotation.*;

/**
 * 内部字典变动时，自动维护字典项
 * 该部分属于程序内字典，与程序交互有关的，不允许外部新增删除，只允许编辑名称
 * 枚举字典用于初始化
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerDictionary {

    /**
     * 字典组编码 为空时使用类名
     */
    String value() default "";

}
