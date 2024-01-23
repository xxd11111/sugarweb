package com.sugarcoat.support.dict.api;

/**
 * 字典
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/29
 */
public interface Dictionary {

    String getId();

    /**
     * 获取字典组
     */
    String getDictGroup();

    /**
     * 获取字典编码
     */
    String getDictCode();

    /**
     * 获取字典名称
     */
    String getDictName();

}
