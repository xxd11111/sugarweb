package com.sugarweb.param.auto;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.reflect.ClassPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.framework.auto.AbstractAutoRegistry;
import com.sugarweb.param.ParamProperties;
import com.sugarweb.param.application.ParamDto;
import com.sugarweb.param.application.ParamService;
import com.sugarweb.param.domain.Param;
import com.sugarweb.param.domain.ParamRepository;
import com.sugarweb.param.domain.QParam;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 参数自动注册
 *
 * @author 许向东
 * @version 1.0
 */
public class ParamAutoRegistry extends AbstractAutoRegistry<ParamDto> {

    /**
     * 扫描路径
     */
    private final String scanPackage;

    private final ParamService paramService;

    public ParamAutoRegistry(ParamProperties paramProperties, ParamService paramService) {
        this.scanPackage = paramProperties.getScanPackage();
        this.paramService = paramService;
    }

    @Override
    protected void insert(ParamDto o) {
        paramService.save(o);
    }

    @Override
    protected void merge(ParamDto db, ParamDto scan) {
        //ignore
    }

    @Override
    protected void deleteByCondition(Collection<ParamDto> collection) {
        Iterable<ParamDto> all = paramService.findAll();
        Set<String> removeIds = new HashSet<>();
        for (ParamDto param : all) {
            if (!collection.contains(param)) {
                removeIds.add(param.getId());
            }
        }
        paramService.removeByIds(removeIds);
    }

    @Override
    protected ParamDto selectOne(ParamDto o) {
        return paramService.findByCode(o.getCode()).orElse(null);
    }

    @Override
    public Collection<ParamDto> scan() {
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
        List<ParamDto> dictGroups = new ArrayList<>();
        if (Iterables.isEmpty(classes)) {
            return dictGroups;
        }
        //遍历class
        Collection<ParamDto> paramDtos = new ArrayList<>();

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
                ParamDto paramDto = new ParamDto();
                paramDto.setCode(code);
                paramDto.setName(name);
                paramDto.setValue(value);
                paramDto.setComment(name);
                paramDtos.add(paramDto);
            }
        }
        return paramDtos;
    }
}
