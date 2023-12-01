package com.sugarcoat.support.parameter.domain;

import java.util.Collection;

/**
 * 参数扫描
 *
 * @author xxd
 * @since 2023/9/5 22:09
 */
public interface ParamScanner {

    Collection<SugarcoatParameter> scan();

}
