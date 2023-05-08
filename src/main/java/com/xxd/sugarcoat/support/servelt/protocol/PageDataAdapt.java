package com.xxd.sugarcoat.support.servelt.protocol;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/4/25
 */
public interface PageDataAdapt {
    /**
     * 转为pageData
     *
     * @param t
     * @return
     */
    <T> PageData<T> convert(Object t, Class<T> clazz);
}
