package com.sugarweb.support.param.auto;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.reflect.ClassPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.exception.FrameworkException;
import com.sugarweb.support.param.api.InnerParam;
import com.sugarweb.support.param.api.ParamItem;
import com.sugarweb.auto.AbstractAutoRegistry;
import com.sugarweb.support.param.ParamProperties;
import com.sugarweb.support.param.domain.Param;
import com.sugarweb.support.param.domain.QParam;
import com.sugarweb.support.param.domain.ParamRepository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 参数自动注册
 *
 * @author 许向东
 * @version 1.0
 */
public class ParamAutoRegistry extends AbstractAutoRegistry<Param> {

    /**
     * 扫描路径
     */
    private final String scanPackage;

    private final ParamRepository paramRepository;

    public ParamAutoRegistry(ParamProperties paramProperties, ParamRepository paramRepository) {
        this.scanPackage = paramProperties.getScanPackage();
        this.paramRepository = paramRepository;
    }

    @Override
    protected void insert(Param o) {
        paramRepository.save(o);
    }

    @Override
    protected void merge(Param db, Param scan) {
        //ignore
    }

    @Override
    protected void deleteByCondition(Collection<Param> collection) {
        Iterable<Param> all = paramRepository.findAll();
        List<String> removeIds = new ArrayList<>();
        for (Param param : all) {
            if (!collection.contains(param)) {
                removeIds.add(param.getId());
            }
        }
        paramRepository.deleteAllById(removeIds);
    }

    @Override
    protected Param selectOne(Param o) {
        QParam query = QParam.param;
        BooleanExpression eq = query.code.eq(o.getCode());
        return paramRepository.findOne(eq).orElse(null);
    }

    @Override
    public Collection<Param> scan() {
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
            InnerParam annotation = load.getAnnotation(InnerParam.class);
            if (annotation != null) {
                classes.add(load);
            }
        }
        List<Param> dictGroups = new ArrayList<>();
        if (Iterables.isEmpty(classes)) {
            return dictGroups;
        }
        //遍历class
        Collection<Param> sugarcoatParams = new ArrayList<>();

        for (Class<?> clazz : classes) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                ParamItem annotation = field.getAnnotation(ParamItem.class);
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
                Param sugarcoatParam = new Param();
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
