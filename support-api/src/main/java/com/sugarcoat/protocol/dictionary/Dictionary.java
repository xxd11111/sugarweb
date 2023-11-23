package com.sugarcoat.protocol.dictionary;

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
    String getGroup();

    /**
     * 获取字典编码
     */
    String getCode();

    /**
     * 获取字典名称
     */
    String getName();

}
