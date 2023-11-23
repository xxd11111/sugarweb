package com.sugarcoat.protocol.parameter;

import java.util.Collection;
import java.util.Optional;

/**
 * 参数管理
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
public interface ParameterManager {

    /**
     * 获取参数值
     *
     * @param code 参数编码
     * @return 参数值
     */
    Optional<String> getValue(String code);

    /**
     * 保存参数
     *
     * @param parameter 参数
     */
    void save(Parameter parameter);

    /**
     * 根据参数编码删除参数
     *
     * @param code 参数编码
     */
    void remove(String code);

    /**
     * 获取参数
     *
     * @param code 参数编码
     * @return 参数
     */
    Optional<Parameter> getParameter(String code);

    /**
     * 获取全部系统参数
     *
     * @return 参数集合
     */
    Collection<Parameter> getAll();

    /**
     * 批量保存
     *
     * @param parameters 参数
     */
    void batchSave(Collection<Parameter> parameters);

}
