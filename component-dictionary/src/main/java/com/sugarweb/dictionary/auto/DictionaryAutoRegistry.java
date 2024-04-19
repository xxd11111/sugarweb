package com.sugarweb.dictionary.auto;

import cn.hutool.core.util.ClassUtil;
import com.sugarweb.dictionary.application.dto.DictionaryDto;
import com.sugarweb.framework.auto.AbstractAutoRegistry;
import com.sugarweb.dictionary.application.DictionaryService;
import com.sugarweb.dictionary.domain.Dictionary;
import com.sugarweb.framework.exception.FrameworkException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 字典自动注册处理
 *
 * @author 许向东
 * @version 1.0
 */
public class DictionaryAutoRegistry extends AbstractAutoRegistry<DictionaryDto> {

    private String classPath = "";

    private final DictionaryService dictionaryService;

    public DictionaryAutoRegistry(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    protected void insert(DictionaryDto o) {
        dictionaryService.save(o);
    }

    @Override
    protected void merge(DictionaryDto db, DictionaryDto scan) {
        //ignore
    }

    @Override
    protected void deleteByCondition(Collection<DictionaryDto> scans) {
        Collection<DictionaryDto> all = dictionaryService.findAll();
        Set<String> removeIds = new HashSet<>();
        for (DictionaryDto db : all) {
            boolean contain = false;
            for (DictionaryDto scan : scans) {
                if (Objects.equals(scan.getDictGroup(), db.getDictGroup()) &&
                        Objects.equals(scan.getDictCode(), db.getDictCode())) {
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                removeIds.add(db.getId());
            }
        }
        dictionaryService.removeByIds(removeIds);
    }

    @Override
    protected DictionaryDto selectOne(DictionaryDto o) {
        return dictionaryService.findByCode(o.getDictGroup(), o.getDictCode()).orElse(null);
    }

    @Override
    public Collection<DictionaryDto> scan() {
        //获取包下class
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(classPath, InnerDictionary.class);
        for (Class<?> load : classes) {
            InnerDictionary annotation = load.getAnnotation(InnerDictionary.class);
            if (annotation != null) {
                classes.add(load);
            }
        }
        List<DictionaryDto> dictionaryDtos = new ArrayList<>();
        for (Class<?> clazz : classes) {
            InnerDictionary innerDictionary = clazz.getAnnotation(InnerDictionary.class);
            String group;
            if (innerDictionary.value() == null || innerDictionary.value().isEmpty()) {
                group = clazz.getSimpleName();
            } else {
                group = innerDictionary.value();
            }
            if (clazz.isEnum()) {
                Field dictCode = null;
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.isEnumConstant()) {
                        continue;
                    }
                    DictionaryCode tag = field.getAnnotation(DictionaryCode.class);
                    if (tag != null) {
                        if (dictCode != null) {
                            throw new FrameworkException("DictionaryCode不能存在两个，请检查 {}", clazz.getName());
                        }
                        dictCode = field;
                    }
                }

                Object[] enumConstants = clazz.getEnumConstants();
                Field[] enumInstances = clazz.getFields();
                Map<Enum<?>, Field> objectObjectHashMap = new HashMap<>();

                for (Object enumConstant : enumConstants) {
                    if (enumConstant instanceof Enum<?> e) {
                        String name = e.name();
                        for (Field enumInstance : enumInstances) {
                            if (name.equals(enumInstance.getName())) {
                                objectObjectHashMap.put(e, enumInstance);
                                break;
                            }
                        }
                    }
                }

                Field finalDictCode = dictCode;
                objectObjectHashMap.forEach((k, v) -> {
                    DictionaryItem dictionaryItem = v.getAnnotation(DictionaryItem.class);
                    String code = null;
                    String name = null;
                    if (dictionaryItem != null) {
                        name = dictionaryItem.value();
                        if (finalDictCode != null) {
                            try {
                                finalDictCode.setAccessible(true);
                                code = (String) finalDictCode.get(k);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            code = dictionaryItem.code();
                        }
                    }

                    if (code == null || code.isEmpty()) {
                        code = k.name();
                    }

                    if (name == null || name.isEmpty()) {
                        name = k.name();
                    }

                    DictionaryDto dictionaryDto = new DictionaryDto();
                    dictionaryDto.setDictCode(code);
                    dictionaryDto.setDictName(name);
                    dictionaryDto.setDictGroup(group);
                    dictionaryDto.setDictType("1");
                    dictionaryDtos.add(dictionaryDto);
                });


            } else {
                Constructor<?>[] constructors = clazz.getConstructors();
                Object o = null;
                try {
                    o = constructors[0].newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    DictionaryItem dictionaryItem = declaredField.getAnnotation(DictionaryItem.class);
                    String name;
                    if (dictionaryItem == null) {
                        continue;
                    } else {
                        name = dictionaryItem.value();
                    }
                    String code = null;
                    try {
                        code = (String) declaredField.get(o);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    DictionaryDto dictionaryDto = new DictionaryDto();
                    dictionaryDto.setDictCode(code);
                    dictionaryDto.setDictName(name);
                    dictionaryDto.setDictGroup(group);
                    dictionaryDto.setDictType("1");
                    dictionaryDtos.add(dictionaryDto);
                }
            }
        }
        return dictionaryDtos;
    }
}
