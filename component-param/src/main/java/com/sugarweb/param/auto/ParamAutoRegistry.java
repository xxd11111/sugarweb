package com.sugarweb.param.auto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.auto.AbstractAutoRegistry;
import com.sugarweb.framework.exception.FrameworkException;
import com.sugarweb.param.ParamProperties;
import com.sugarweb.param.application.ParamDto;
import com.sugarweb.param.application.ParamService;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 参数自动注册
 *
 * @author xxd
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


    protected void insert(ParamDto o) {
        paramService.save(o);
    }


    protected void merge(ParamDto db, ParamDto scan) {
        //ignore
    }


    protected void deleteByCondition(Collection<ParamDto> collection) {
        Iterable<ParamDto> all = paramService.findAll();
        Set<String> removeIds = new HashSet<>();
        for (ParamDto param : all) {
            if (!collection.contains(param)) {
                removeIds.add(param.getParamId());
            }
        }
        paramService.removeByIds(removeIds);
    }


    protected ParamDto selectOne(ParamDto o) {
        return paramService.getByCode(o.getParamCode());
    }


    public Collection<ParamDto> scan() {
        //获取包下class
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(scanPackage, InnerParam.class);
        for (Class<?> load : classes) {
            InnerParam annotation = load.getAnnotation(InnerParam.class);
            if (annotation != null) {
                classes.add(load);
            }
        }
        List<ParamDto> dictGroups = new ArrayList<>();
        if (CollUtil.isEmpty(classes)) {
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
                ParamDto paramDto = new ParamDto();
                paramDto.setParamCode(code);
                paramDto.setParamName(name);
                paramDto.setParamValue(value);
                paramDto.setParamComment(name);
                paramDtos.add(paramDto);
            }
        }
        return paramDtos;
    }
}
