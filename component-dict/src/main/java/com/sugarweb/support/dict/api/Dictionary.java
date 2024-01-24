package com.sugarweb.support.dict.api;

/**
 * 字典
 *
 * @author xxd
 * @version 1.0
 * @version 1.0
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
