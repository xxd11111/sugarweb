package com.sugarcoat.support.parameter.auto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.parameter.InnerParameter;
import com.sugarcoat.protocol.parameter.ParameterItem;
import com.sugarcoat.support.orm.auto.AbstractAutoRegistry;
import com.sugarcoat.support.parameter.ParamProperties;
import com.sugarcoat.support.parameter.domain.QSugarcoatParameter;
import com.sugarcoat.support.parameter.domain.SgcParamRepository;
import com.sugarcoat.support.parameter.domain.SugarcoatParameter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/12/15
 */
public class ParameterAutoRegistry extends AbstractAutoRegistry<SugarcoatParameter> {

    /**
     * 扫描路径
     */
    private final String scanPackage;

    private final SgcParamRepository paramRepository;

    public ParameterAutoRegistry(ParamProperties paramProperties, SgcParamRepository paramRepository) {
        this.scanPackage = paramProperties.getScanPackage();
        this.paramRepository = paramRepository;
    }

    @Override
    protected void insert(SugarcoatParameter o) {
        paramRepository.save(o);
    }

    @Override
    protected void merge(SugarcoatParameter db, SugarcoatParameter scan) {
        //ignore
    }

    @Override
    protected void deleteByCondition(Collection<SugarcoatParameter> collection) {
        Iterable<SugarcoatParameter> all = paramRepository.findAll();
        List<String> removeIds = new ArrayList<>();
        for (SugarcoatParameter sugarcoatParameter : all) {
            if (!collection.contains(sugarcoatParameter)) {
                removeIds.add(sugarcoatParameter.getId());
            }
        }
        paramRepository.deleteAllById(removeIds);
    }

    @Override
    protected SugarcoatParameter selectOne(SugarcoatParameter o) {
        QSugarcoatParameter query = QSugarcoatParameter.sugarcoatParameter;
        BooleanExpression eq = query.code.eq(o.getCode());
        return paramRepository.findOne(eq).orElse(null);
    }

    @Override
    public Collection<SugarcoatParameter> scan() {
        //获取包下class
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(scanPackage, InnerParameter.class);
        List<SugarcoatParameter> dictGroups = new ArrayList<>();
        if (CollUtil.isEmpty(classes)) {
            return dictGroups;
        }
        //遍历class
        Collection<SugarcoatParameter> sugarcoatParams = new ArrayList<>();

        for (Class<?> clazz : classes) {
            Field[] declaredFields = clazz.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(declaredFields)) {
                for (Field field : declaredFields) {
                    ParameterItem annotation = field.getAnnotation(ParameterItem.class);
                    if (annotation == null){
                        continue;
                    }
                    String code = annotation.code();
                    String name = annotation.name();
                    String value = annotation.value();

                    if (StrUtil.isEmpty(value)) {
                        throw new FrameworkException("未设置默认值，请检查：{}", clazz.getSimpleName());
                    }
                    //为空时候取字段名称
                    if (StrUtil.isEmpty(code)) {
                        code = field.getName();
                    }
                    //为空时候取字段名称
                    if (StrUtil.isEmpty(name)) {
                        value = field.getName();
                    }
                    //创建参数对象
                    SugarcoatParameter sugarcoatParam = new SugarcoatParameter();
                    sugarcoatParam.setCode(code);
                    sugarcoatParam.setName(name);
                    sugarcoatParam.setValue(value);
                    sugarcoatParam.setComment(name);
                    sugarcoatParams.add(sugarcoatParam);
                }
            }
        }
        return sugarcoatParams;
    }
}
