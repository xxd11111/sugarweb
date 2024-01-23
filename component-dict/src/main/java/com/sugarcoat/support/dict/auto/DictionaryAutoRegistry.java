package com.sugarcoat.support.dict.auto;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.sugarcoat.support.dict.api.DictionaryCode;
import com.sugarcoat.support.dict.api.DictionaryItem;
import com.sugarcoat.support.dict.api.InnerDictionary;
import com.xxd.auto.AbstractAutoRegistry;
import com.sugarcoat.support.dict.api.DictionaryManager;
import com.sugarcoat.support.dict.domain.SugarcoatDictionary;
import com.xxd.exception.FrameworkException;

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
public class DictionaryAutoRegistry extends AbstractAutoRegistry<SugarcoatDictionary> {

    private final DictionaryManager<SugarcoatDictionary> dictionaryManager;

    public DictionaryAutoRegistry(DictionaryManager<SugarcoatDictionary> dictionaryManager) {
        this.dictionaryManager = dictionaryManager;
    }

    @Override
    protected void insert(SugarcoatDictionary o) {
        dictionaryManager.put(o);
    }

    @Override
    protected void merge(SugarcoatDictionary db, SugarcoatDictionary scan) {
        //ignore
    }

    @Override
    protected void deleteByCondition(Collection<SugarcoatDictionary> scans) {
        Collection<SugarcoatDictionary> all = dictionaryManager.getAll();
        Collection<String> removeIds = new ArrayList<>();
        for (SugarcoatDictionary db : all) {
            boolean contain = false;
            for (SugarcoatDictionary scan : scans) {
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
        dictionaryManager.removeByIds(removeIds);
    }

    @Override
    protected SugarcoatDictionary selectOne(SugarcoatDictionary o) {
        return dictionaryManager.get(o.getDictGroup(), o.getDictCode()).orElse(null);
    }

    @Override
    public Collection<SugarcoatDictionary> scan() {
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
        List<SugarcoatDictionary> sugarcoatDictionaries = new ArrayList<>();
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

                    SugarcoatDictionary sugarcoatDictionary = new SugarcoatDictionary();
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
                    SugarcoatDictionary sugarcoatDictionary = new SugarcoatDictionary();
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
