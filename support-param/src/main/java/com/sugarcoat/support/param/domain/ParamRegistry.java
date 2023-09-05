package com.sugarcoat.support.param.domain;

import java.util.Collection;

/**
 * 参数注册
 *
 * @author xxd
 * @since 2023/9/5 22:09
 */
public interface ParamRegistry {

    void register(Collection<SugarcoatParam> sugarcoatParams);

}
