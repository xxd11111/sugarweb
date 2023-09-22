package com.sugarcoat.support.param.domain;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.protocol.param.Param;
import com.sugarcoat.protocol.param.ParamManager;
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
public class DefaultParamManager implements ParamManager {

    private final SugarcoatParamRepository paramRepository;

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
                    .map(SugarcoatParam::getValue);
        }
    }

    @Override
    public void save(Param param) {
        if (param instanceof SugarcoatParam sugarcoatParam) {
            paramRepository.save(sugarcoatParam);
            paramCacheManager.put(sugarcoatParam);
        } else {
            throw new FrameworkException("Param can not cast to SugarcoatParam");
        }
    }

    @Override
    public void remove(String code) {
        SugarcoatParam param = paramRepository.findOne(QSugarcoatParam.sugarcoatParam.code.eq(code))
                .orElseThrow(() -> new ValidateException("not find Param"));
        paramRepository.delete(param);
        paramCacheManager.remove(code);
    }

    @Override
    public Optional<Param> getParameter(String code) {
        //由于get属性只有两个，当前不采用json方式
        if (StrUtil.isBlank(code)) {
            return Optional.empty();
        }
        Optional<String> cacheValue = paramCacheManager.get(code);
        //注意泛型擦除问题
        if (cacheValue.isPresent()) {
            return Optional.of(new Param() {
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

    //todo
    @Override
    public Collection<Param> getAll() {
        Collection<Param> params = new ArrayList<>();
        Iterable<SugarcoatParam> all = paramRepository.findAll();
        all.forEach(params::add);
        return params;
    }

    @Override
    public void batchSave(Collection<Param> params) {
        List<SugarcoatParam> sugarcoatParams = new ArrayList<>();
        for (Param param : params) {
            if (param instanceof SugarcoatParam sugarcoatParam) {
                sugarcoatParams.add(sugarcoatParam);
            } else {
                throw new FrameworkException("Param can not cast to SugarcoatParam");
            }
        }
        paramRepository.saveAll(sugarcoatParams);
        paramCacheManager.put(params);
    }
}
