package com.sugarcoat.support.param.domain;

import cn.hutool.core.util.StrUtil;
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

    private final ParamCacheManager paramCacheManager;

    @Override
    public Optional<String> getValue(String code) {
        if (StrUtil.isBlank(code)) {
            return Optional.empty();
        }
        Optional<String> cacheValue = paramCacheManager.get(code);
        if (cacheValue.isPresent()) {
            return cacheValue;
        } else {
            return paramRepository.findOne(QSugarcoatParam.sugarcoatParam.code.eq(code))
                    .map(SugarcoatParameter::getValue);
        }
    }

    @Override
    public void save(Parameter parameter) {
        if (parameter instanceof SugarcoatParameter sugarcoatParam) {
            paramRepository.save(sugarcoatParam);
            paramCacheManager.put(sugarcoatParam);
        } else {
            throw new FrameworkException("Param can not cast to SugarcoatParam");
        }
    }

    @Override
    public void remove(String code) {
        SugarcoatParameter param = paramRepository.findOne(QSugarcoatParam.sugarcoatParam.code.eq(code))
                .orElseThrow(() -> new ValidateException("not find Param"));
        paramRepository.delete(param);
        paramCacheManager.remove(code);
    }

    @Override
    public Optional<Parameter> getParameter(String code) {
        //由于get属性只有两个，当前不采用json方式
        if (StrUtil.isBlank(code)) {
            return Optional.empty();
        }
        Optional<String> cacheValue = paramCacheManager.get(code);
        //注意泛型擦除问题
        if (cacheValue.isPresent()) {
            return Optional.of(new Parameter() {
                @Override
                public String getCode() {
                    return code;
                }

                @Override
                public String getValue() {
                    return cacheValue.get();
                }
            });
        } else {
            return paramRepository.findOne(QSugarcoatParam.sugarcoatParam.code.eq(code)).map(a -> a);
        }
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
        paramCacheManager.put(parameters);
    }
}
