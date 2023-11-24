package com.sugarcoat.support.orm.auto;

/**
 * 字典注册策略
 *
 * @author xxd
 * @since 2023/9/2 21:45
 */
public enum RegistryStrategy {

    /**
     * 关闭（默认）
     */
    DISABLE,

    /**
     * 新增
     */
    INSERT,

    /**
     * 合并
     */
    MERGE,

    /**
     * 覆盖
     */
    OVERRIDE;

}
