package com.sugarcoat.support.param.domain;

import com.sugarcoat.protocol.param.Param;

import java.util.Collection;
import java.util.Optional;

/**
 * 参数缓存
 *
 * @author xxd
 * @since 2023/9/5 22:11
 */
public interface ParamCacheManager {

    /**
     * 保存缓存
     * @param code 字典组编码
     * @param value 字典编码
     */
    void put(String code, String value);

    /**
     * 保存缓存
     * @param param 参数
     */
    default void put(Param param) {
        put(param.getCode(), param.getValue());
    }

    /**
     * 保存缓存
     * @param params 参数列表
     */
    void put(Collection<Param> params);

    /**
     * 获取参数值
     * @param code 参数编码
     * @return 参数值
     */
    Optional<String> get(String code);

    /**
     * 删除
     * @param code 参数编码
     */
    void remove(String code);

    /**
     * 清空参数缓存
     */
    void clean();

}
