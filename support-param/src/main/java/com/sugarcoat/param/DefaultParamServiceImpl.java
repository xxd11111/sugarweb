package com.sugarcoat.param;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.param.api.ParamDTO;
import com.sugarcoat.param.api.ParamQueryVO;
import com.sugarcoat.param.api.ParamService;
import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

/**
 * @author xxd
 * @description 默认缓存实现方法
 * @date 2022-11-19
 */
@RequiredArgsConstructor
public class DefaultParamServiceImpl implements ParamService {

    private final ParamRepository paramRepository;

    @Override
    public void save(ParamDTO paramDTO) {
        Param entity = new Param();
        if (StrUtil.isBlank(paramDTO.getId())) {
            entity.setCode(paramDTO.getCode());
            entity.setName(paramDTO.getName());
            entity.setValue(paramDTO.getValue());
            entity.setDefaultValue(paramDTO.getDefaultValue());
            entity.setComment(paramDTO.getComment());
        } else {
            entity.setId(paramDTO.getId());
            entity.setName(paramDTO.getName());
            entity.setValue(paramDTO.getValue());
            entity.setComment(paramDTO.getComment());
        }
        paramRepository.save(entity);
    }

    @Override
    public void remove(String id) {
        paramRepository.deleteById(id);
    }

    @Override
    public void reset(String id) {
        Param param = paramRepository.findById(id)
                .orElseThrow(() -> new ValidateException("param not find"));
        param.resetValue();
        paramRepository.save(param);
    }

    @Override
    public ParamDTO findByCode(String code) {
        Param param = paramRepository.findOne(QParam.param.code.eq(code))
                .orElseThrow(() -> new ValidateException("param not find"));
        return getParamDTO(param);
    }

    @Override
    public ParamDTO findById(String id) {
        Param param = paramRepository.findById(id)
                .orElseThrow(() -> new ValidateException("param not find"));
        return getParamDTO(param);
    }

    @Override
    public PageData<ParamDTO> findPage(PageParam pageParam, ParamQueryVO cmd) {
        PageRequest pageRequest = PageRequest.of(pageParam.getPageNum(), pageParam.getPageSize());
        Page<ParamDTO> page = paramRepository.findAll(Expressions.TRUE, pageRequest).map(this::getParamDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public void remove(Set<String> ids) {
        paramRepository.deleteAllById(ids);
    }

    @Override
    public void reset(Set<String> ids) {
        Iterable<Param> params = paramRepository.findAllById(ids);
        params.forEach(Param::resetValue);
        paramRepository.saveAll(params);
    }

    private ParamDTO getParamDTO(Param param) {
        ParamDTO paramDTO = new ParamDTO();
        paramDTO.setId(param.getId());
        paramDTO.setCode(param.getCode());
        paramDTO.setName(param.getName());
        paramDTO.setValue(param.getValue());
        paramDTO.setDefaultValue(param.getDefaultValue());
        paramDTO.setComment(param.getComment());
        return paramDTO;
    }
}
