package com.sugarcoat.support.orm.auto;

import java.util.Collection;

/**
 * 扫描者
 *
 * @author 许向东
 * @date 2023/11/22
 */
public interface Scanner<T> {

    Collection<T> scan();

}
