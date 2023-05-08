package com.xxd.sugarcoat.support.prod.param.api;

import com.xxd.sugarcoat.support.prod.common.PageParam;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * @author xxd
 * @description 参数缓存
 * @date 2022-11-18
 */
public interface ParamService {

    void save(ParamDTO paramDTO);

    void remove(String id);

    void reset(String id);

    ParamDTO findByCode(String code);

    ParamDTO findById(String id);

    Page<ParamDTO> findPage(PageParam pageParam, ParamQueryVO cmd);

    void remove(Set<String> ids);

    void reset(Set<String> ids);

}
