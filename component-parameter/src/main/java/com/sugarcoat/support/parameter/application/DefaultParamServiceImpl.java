package com.sugarcoat.support.parameter.application;

import com.querydsl.core.types.dsl.Expressions;
import com.xxd.common.PageData;
import com.xxd.common.PageRequest;
import com.xxd.exception.ValidateException;
import com.sugarcoat.support.parameter.domain.QSugarcoatParameter;
import com.sugarcoat.support.parameter.domain.SgcParamRepository;
import com.sugarcoat.support.parameter.domain.SugarcoatParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * 参数服务实现类
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
public class DefaultParamServiceImpl implements ParamService {

    private final SgcParamRepository sugarcoatParamRepository;

    @Override
    public void save(ParamDto paramDto) {
        SugarcoatParameter param = sugarcoatParamRepository.findById(paramDto.getId())
                .orElseThrow(() -> new ValidateException("not find param"));
        //更新
        param.setName(paramDto.getName());
        param.setValue(paramDto.getValue());
        param.setComment(paramDto.getComment());
        sugarcoatParamRepository.save(param);
    }

    @Override
    public ParamDto findByCode(String code) {
        SugarcoatParameter sugarcoatParam = sugarcoatParamRepository
                .findOne(QSugarcoatParameter.sugarcoatParameter.code.eq(code))
                .orElseThrow(() -> new ValidateException("param not find"));
        return ParamConvert.getParamDTO(sugarcoatParam);
    }

    @Override
    public ParamDto findById(String id) {
        SugarcoatParameter sugarcoatParam = sugarcoatParamRepository.findById(id)
                .orElseThrow(() -> new ValidateException("param not find"));
        return ParamConvert.getParamDTO(sugarcoatParam);
    }

    @Override
    public PageData<ParamDto> findPage(PageRequest pageDto, ParamQueryDto cmd) {
        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize());
        Page<ParamDto> page = sugarcoatParamRepository.findAll(Expressions.TRUE, pageRequest)
                .map(ParamConvert::getParamDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public void reset(Set<String> ids) {
        Iterable<SugarcoatParameter> params = sugarcoatParamRepository.findAllById(ids);
        sugarcoatParamRepository.saveAll(params);
    }

}
