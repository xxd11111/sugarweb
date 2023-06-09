package com.sugarcoat.api.dict;

import java.lang.annotation.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 枚举字典
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/21
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerDictionary {
    AbstractQueuedSynchronizer
    String groupCode();
}
