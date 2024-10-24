package com.sugarweb.dict.auto;

import java.lang.annotation.*;

/**
 * 内部字典变动时，自动维护字典项
 * 该部分属于程序内字典，与程序交互有关的，不允许外部新增删除，只允许编辑名称
 * 枚举字典用于初始化
 *
 * @author xxd
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerDict {

    /**
     * 字典组编码 为空时使用类名
     */
    String value() default "";

}
