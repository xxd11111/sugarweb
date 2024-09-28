package com.sugarweb.framework.auto;

import java.util.Collection;

/**
 * 扫描者
 *
 * @author xxd
 * @version 1.0
 */
public interface Scanner<T> {

    Collection<T> scan();

}
