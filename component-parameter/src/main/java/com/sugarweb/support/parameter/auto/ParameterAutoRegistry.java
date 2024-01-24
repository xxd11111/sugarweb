package com.sugarweb.support.parameter.auto;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.reflect.ClassPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.exception.FrameworkException;
import com.sugarweb.support.parameter.api.InnerParameter;
import com.sugarweb.support.parameter.api.ParameterItem;
import com.sugarweb.auto.AbstractAutoRegistry;
import com.sugarweb.support.parameter.ParamProperties;
import com.sugarweb.support.parameter.domain.QSugarcoatParameter;
import com.sugarweb.support.parameter.domain.BaseParamRepository;
import com.sugarweb.support.parameter.domain.SugarcoatParameter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 参数自动注册
 *
 * @author 许向东
 * @version 1.0
 */
public class ParameterAutoRegistry extends AbstractAutoRegistry<SugarcoatParameter> {

    /**
     * 扫描路径
     */
    private final String scanPackage;

    private final BaseParamRepository paramRepository;

    public ParameterAutoRegistry(ParamProperties paramProperties, BaseParamRepository paramRepository) {
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
        ClassPath classpath = null;
        try {
            classpath = ClassPath.from(this.getClass().getClassLoader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImmutableSet<ClassPath.ClassInfo> classInfos = classpath.getTopLevelClasses(scanPackage);
        //获取包下class
        Set<Class<?>> classes = new HashSet<>();
        for (ClassPath.ClassInfo classInfo : classInfos) {
            Class<?> load = classInfo.load();
            InnerParameter annotation = load.getAnnotation(InnerParameter.class);
            if (annotation != null) {
                classes.add(load);
            }
        }
        List<SugarcoatParameter> dictGroups = new ArrayList<>();
        if (Iterables.isEmpty(classes)) {
            return dictGroups;
        }
        //遍历class
        Collection<SugarcoatParameter> sugarcoatParams = new ArrayList<>();

        for (Class<?> clazz : classes) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                ParameterItem annotation = field.getAnnotation(ParameterItem.class);
                if (annotation == null) {
                    continue;
                }
                String code = annotation.code();
                String name = annotation.name();
                String value = annotation.value();

                if (Strings.isNullOrEmpty(value)) {
                    throw new FrameworkException("未设置默认值，请检查：{}", clazz.getSimpleName());
                }
                //为空时候取字段名称
                if (Strings.isNullOrEmpty(code)) {
                    code = field.getName();
                }
                //为空时候取字段名称
                if (Strings.isNullOrEmpty(name)) {
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
        return sugarcoatParams;
    }
}
