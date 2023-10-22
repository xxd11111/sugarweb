package com.sugarcoat.support.param.application;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.support.param.domain.QSugarcoatParam;
import com.sugarcoat.support.param.domain.SgcParamRepository;
import com.sugarcoat.support.param.domain.SugarcoatParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

/**
 * @author xxd
 * @description 默认缓存实现方法
 * @since 2022-11-19
 */
@RequiredArgsConstructor
public class DefaultParamServiceImpl implements ParamService {

    private final SgcParamRepository sugarcoatParamRepository;

    @Override
    public void save(ParamDto paramDto) {
        SugarcoatParam param = sugarcoatParamRepository.findById(paramDto.getId())
                .orElseThrow(() -> new ValidateException("not find param"));
        //更新
        param.setName(paramDto.getName());
        param.setValue(paramDto.getValue());
        param.setComment(paramDto.getComment());
        sugarcoatParamRepository.save(param);
    }

    @Override
    public ParamDto findByCode(String code) {
        SugarcoatParam sugarcoatParam = sugarcoatParamRepository
                .findOne(QSugarcoatParam.sugarcoatParam.code.eq(code))
                .orElseThrow(() -> new ValidateException("param not find"));
        return ParamConvert.getParamDTO(sugarcoatParam);
    }

    @Override
    public ParamDto findById(String id) {
        SugarcoatParam sugarcoatParam = sugarcoatParamRepository.findById(id)
                .orElseThrow(() -> new ValidateException("param not find"));
        return ParamConvert.getParamDTO(sugarcoatParam);
    }

    @Override
    public PageData<ParamDto> findPage(PageDto pageDto, ParamQueryDto cmd) {
        PageRequest pageRequest = PageRequest.of(pageDto.getPage(), pageDto.getSize());
        Page<ParamDto> page = sugarcoatParamRepository.findAll(Expressions.TRUE, pageRequest)
                .map(ParamConvert::getParamDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public void reset(Set<String> ids) {
        Iterable<SugarcoatParam> params = sugarcoatParamRepository.findAllById(ids);
        params.forEach(SugarcoatParam::resetValue);
        sugarcoatParamRepository.saveAll(params);
    }

}
