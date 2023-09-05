package com.sugarcoat.api.param;

import java.util.Collection;
import java.util.Optional;

/**
 * 参数管理
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
public interface ParamManager {

    /**
     * 获取参数值
     *
     * @param code 参数编码
     * @return 参数值
     */
    String getValue(String code);

    /**
     * 保存参数
     *
     * @param param 参数
     * @return 参数code
     */
    String save(Param param);

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
    Optional<Param> getParameter(String code);

    /**
     * 获取全部系统参数
     *
     * @return 参数集合
     */
    Collection<Param> getAll();

    /**
     * 批量保存
     *
     * @param params 参数
     */
    void batchSave(Collection<Param> params);

}
