package com.sugarcoat.support.param.domain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * 参数注册
 *
 * @author xxd
 * @since 2023/9/5 22:09
 */
@RequiredArgsConstructor
public class DefaultParamRegistry implements ParamRegistry {

    private final SugarcoatParamRepository paramRepository;

    /**
     * 注册系统参数，执行规则如下
     * <p>
     * // 相同的参数 只更新默认值
     * // 若db里的参数value 与 defaultValue相同则同时更新为inner参数的value
     * <p>
     * //        code value default     a---> result        b--> result          c---> result
     * //  db        1     a     b         a     a            ignore update      a    c
     * //  db        1     a     a       ignore update      b     b              a    c
     * //  db        1     b     a       ignore update      b     b              b    c
     * //inner       1           a
     * //inner       1           b
     * //inner       1           c
     *
     * @param innerParams 系统内置参数
     */
    @Override
    public void register(Collection<SugarcoatParam> innerParams) {
        if (CollUtil.isEmpty(innerParams)) {
            paramRepository.deleteAll();
            return;
        }
        // 获取全部
        Iterable<SugarcoatParam> dbParams = paramRepository.findAll();
        Collection<String> removeIds = new ArrayList<>();
        Collection<SugarcoatParam> updateParams = new ArrayList<>();
        for (SugarcoatParam dbParam : dbParams) {
            Optional<SugarcoatParam> innerParamOptional = innerParams.stream()
                    .filter(param -> StrUtil.equals(dbParam.getCode(), param.getCode()))
                    .findAny();
            boolean exist = innerParamOptional.isPresent();
            // 相同的参数 只更新默认值
            if (exist) {
                SugarcoatParam innerParam = innerParamOptional.get();
                //当inner默认值与db默认值不相同时，执行以下操作；相同时，跳过
                if (!StrUtil.equals(innerParam.getDefaultValue(), dbParam.getDefaultValue())) {
                    //若db默认值与value相同时，额外更新value
                    if (StrUtil.equals(dbParam.getDefaultValue(), dbParam.getValue())) {
                        dbParam.setValue(innerParam.getDefaultValue());
                    }
                    //更新默认值
                    dbParam.setDefaultValue(innerParam.getDefaultValue());
                    updateParams.add(dbParam);
                }
            } else {
                // 匹配出不存在ids，删除
                removeIds.add(dbParam.getId());
            }
        }
        // 更新结果
        paramRepository.saveAll(updateParams);
        // 删除结果
        paramRepository.deleteAllById(removeIds);

    }
}
