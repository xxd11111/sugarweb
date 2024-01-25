package com.sugarweb.support.param.application;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.exception.FrameworkException;
import com.sugarweb.exception.ValidateException;
import com.sugarweb.support.param.domain.Param;
import com.sugarweb.support.param.domain.QParam;
import com.sugarweb.support.param.domain.ParamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.*;

/**
 * 参数服务实现类
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
public class ParamServiceImpl implements ParamService {

    private final ParamRepository paramRepository;

    @Override
    public void save(ParamDto paramDto) {
        Param param = paramRepository.findById(paramDto.getId())
                .orElseThrow(() -> new ValidateException("not find param"));
        //更新
        param.setName(paramDto.getName());
        param.setValue(paramDto.getValue());
        param.setComment(paramDto.getComment());
        paramRepository.save(param);
    }

    @Override
    public ParamDto findByCode(String code) {
        Param sugarcoatParam = paramRepository
                .findOne(QParam.param.code.eq(code))
                .orElseThrow(() -> new ValidateException("param not find"));
        return ParamConvert.getParamDTO(sugarcoatParam);
    }

    @Override
    public ParamDto findById(String id) {
        Param sugarcoatParam = paramRepository.findById(id)
                .orElseThrow(() -> new ValidateException("param not find"));
        return ParamConvert.getParamDTO(sugarcoatParam);
    }

    @Override
    public PageData<ParamDto> findPage(PageRequest pageDto, ParamQueryDto cmd) {
        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize());
        Page<ParamDto> page = paramRepository.findAll(Expressions.TRUE, pageRequest)
                .map(ParamConvert::getParamDTO);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public void reset(Set<String> ids) {
        Iterable<Param> params = paramRepository.findAllById(ids);
        paramRepository.saveAll(params);
    }

    @Override
    public Optional<String> getValue(String code) {
        if (Strings.isNullOrEmpty(code)) {
            return Optional.empty();
        }
        return paramRepository.findOne(QParam.param.code.eq(code))
                .map(Param::getValue);
    }

    @Override
    public void save(Param parameter) {
        if (parameter instanceof Param sugarcoatParam) {
            paramRepository.save(sugarcoatParam);
        } else {
            throw new FrameworkException("Param can not cast to SugarcoatParam");
        }
    }

    @Override
    public void remove(String code) {
        Param param = paramRepository.findOne(QParam.param.code.eq(code))
                .orElseThrow(() -> new ValidateException("not find Param"));
        paramRepository.delete(param);
    }

    @Override
    public Optional<Param> getParameter(String code) {
        //由于get属性只有两个，当前不采用json方式
        if (Strings.isNullOrEmpty(code)) {
            return Optional.empty();
        }
        return paramRepository.findOne(QParam.param.code.eq(code)).map(a -> a);
    }

    @Override
    public Collection<Param> getAll() {
        Collection<Param> parameters = new ArrayList<>();
        Iterable<Param> all = paramRepository.findAll();
        all.forEach(parameters::add);
        return parameters;
    }

    @Override
    public void batchSave(Collection<Param> parameters) {
        List<Param> sugarcoatParams = new ArrayList<>();
        for (Param parameter : parameters) {
            if (parameter instanceof Param sugarcoatParam) {
                sugarcoatParams.add(sugarcoatParam);
            } else {
                throw new FrameworkException("Param can not cast to SugarcoatParam");
            }
        }
        paramRepository.saveAll(sugarcoatParams);
    }

}
