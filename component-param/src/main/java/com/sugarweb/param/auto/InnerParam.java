package com.sugarweb.param.auto;

import java.lang.annotation.*;

/**
 * 扫描入口
 *
 * @author xxd
 * @version 1.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerParam {

}
