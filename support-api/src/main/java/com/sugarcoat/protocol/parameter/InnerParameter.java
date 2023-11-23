package com.sugarcoat.protocol.parameter;

import java.lang.annotation.*;

/**
 * 扫描入口
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerParameter {

}
