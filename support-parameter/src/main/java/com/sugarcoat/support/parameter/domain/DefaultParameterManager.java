package com.sugarcoat.support.parameter.domain;

import com.google.common.base.Strings;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.protocol.parameter.Parameter;
import com.sugarcoat.protocol.parameter.ParameterManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 参数管理
 *
 * @author xxd
 * @since 2023/9/6 22:19
 */
@RequiredArgsConstructor
public class DefaultParameterManager implements ParameterManager {

    private final SgcParamRepository paramRepository;

    @Override
    public Optional<String> getValue(String code) {
        if (Strings.isNullOrEmpty(code)) {
            return Optional.empty();
        }
        return paramRepository.findOne(QSugarcoatParameter.sugarcoatParameter.code.eq(code))
                .map(SugarcoatParameter::getValue);
    }

    @Override
    public void save(Parameter parameter) {
        if (parameter instanceof SugarcoatParameter sugarcoatParam) {
            paramRepository.save(sugarcoatParam);
        } else {
            throw new FrameworkException("Param can not cast to SugarcoatParam");
        }
    }

    @Override
    public void remove(String code) {
        SugarcoatParameter param = paramRepository.findOne(QSugarcoatParameter.sugarcoatParameter.code.eq(code))
                .orElseThrow(() -> new ValidateException("not find Param"));
        paramRepository.delete(param);
    }

    @Override
    public Optional<Parameter> getParameter(String code) {
        //由于get属性只有两个，当前不采用json方式
        if (Strings.isNullOrEmpty(code)) {
            return Optional.empty();
        }
        return paramRepository.findOne(QSugarcoatParameter.sugarcoatParameter.code.eq(code)).map(a -> a);
    }

    @Override
    public Collection<Parameter> getAll() {
        Collection<Parameter> parameters = new ArrayList<>();
        Iterable<SugarcoatParameter> all = paramRepository.findAll();
        all.forEach(parameters::add);
        return parameters;
    }

    @Override
    public void batchSave(Collection<Parameter> parameters) {
        List<SugarcoatParameter> sugarcoatParams = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (parameter instanceof SugarcoatParameter sugarcoatParam) {
                sugarcoatParams.add(sugarcoatParam);
            } else {
                throw new FrameworkException("Param can not cast to SugarcoatParam");
            }
        }
        paramRepository.saveAll(sugarcoatParams);
    }
}
