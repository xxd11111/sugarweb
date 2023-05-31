package com.sugarcoat.param.application;

import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParam;

import java.util.Set;

/**
 * 参数缓存
 *
 * @author xxd
 * @date 2022-11-18
 */
public interface ParameterService {

    void save(ParameterDTO parameterDTO);

    void remove(String id);

    void reset(String id);

    ParameterDTO findByCode(String code);

    ParameterDTO findById(String id);

    PageData<ParameterDTO> findPage(PageParam pageParam, ParamQueryCmd cmd);

    void remove(Set<String> ids);

    void reset(Set<String> ids);

}
