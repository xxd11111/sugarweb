package com.sugarcoat.support.orm.datasource;

import java.lang.annotation.*;

/**
 * 动态数据源
 *
 * @author 许向东
 * @date 2023/11/14
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicDS {

    String value() default "master";

}
