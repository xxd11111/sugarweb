package com.sugarweb.dictionary.auto;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
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
public class DictionaryAutoRegistry extends AbstractAutoRegistry<Dictionary> {

    private final DictionaryService dictionaryService;

    public DictionaryAutoRegistry(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    protected void insert(com.sugarweb.dictionary.domain.Dictionary o) {
        dictionaryService.put(o);
    }

    @Override
    protected void merge(com.sugarweb.dictionary.domain.Dictionary db, com.sugarweb.dictionary.domain.Dictionary scan) {
        //ignore
    }

    @Override
    protected void deleteByCondition(Collection<com.sugarweb.dictionary.domain.Dictionary> scans) {
        Collection<com.sugarweb.dictionary.domain.Dictionary> all = dictionaryService.getAll();
        Collection<String> removeIds = new ArrayList<>();
        for (com.sugarweb.dictionary.domain.Dictionary db : all) {
            boolean contain = false;
            for (com.sugarweb.dictionary.domain.Dictionary scan : scans) {
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
    protected com.sugarweb.dictionary.domain.Dictionary selectOne(com.sugarweb.dictionary.domain.Dictionary o) {
        return dictionaryService.get(o.getDictGroup(), o.getDictCode()).orElse(null);
    }

    @Override
    public Collection<com.sugarweb.dictionary.domain.Dictionary> scan() {
        ClassPath classpath = null;
        try {
            classpath = ClassPath.from(this.getClass().getClassLoader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImmutableSet<ClassPath.ClassInfo> classInfos = classpath.getTopLevelClasses("com.sugarcoat.support.dict");
        //获取包下class
        Set<Class<?>> classes = new HashSet<>();
        for (ClassPath.ClassInfo classInfo : classInfos) {
            Class<?> load = classInfo.load();
            InnerDictionary annotation = load.getAnnotation(InnerDictionary.class);
            if (annotation != null) {
                classes.add(load);
            }
        }
        List<com.sugarweb.dictionary.domain.Dictionary> sugarcoatDictionaries = new ArrayList<>();
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

                    com.sugarweb.dictionary.domain.Dictionary sugarcoatDictionary = new com.sugarweb.dictionary.domain.Dictionary();
                    sugarcoatDictionary.setDictCode(code);
                    sugarcoatDictionary.setDictName(name);
                    sugarcoatDictionary.setDictGroup(group);
                    sugarcoatDictionary.setDictType("1");
                    sugarcoatDictionaries.add(sugarcoatDictionary);
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
                    com.sugarweb.dictionary.domain.Dictionary sugarcoatDictionary = new Dictionary();
                    sugarcoatDictionary.setDictCode(code);
                    sugarcoatDictionary.setDictName(name);
                    sugarcoatDictionary.setDictGroup(group);
                    sugarcoatDictionary.setDictType("1");
                    sugarcoatDictionaries.add(sugarcoatDictionary);
                }
            }
        }
        return sugarcoatDictionaries;
    }
}
