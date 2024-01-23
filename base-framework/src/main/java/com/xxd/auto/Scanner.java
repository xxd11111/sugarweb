package com.xxd.auto;

import java.util.Collection;

/**
 * 扫描者
 *
 * @author 许向东
 * @version 1.0
 */
public interface Scanner<T> {

    Collection<T> scan();

}
