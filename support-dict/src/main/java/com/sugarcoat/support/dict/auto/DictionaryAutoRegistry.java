package com.sugarcoat.support.dict.auto;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.dictionary.DictionaryCode;
import com.sugarcoat.protocol.dictionary.DictionaryItem;
import com.sugarcoat.protocol.dictionary.InnerDictionary;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.support.orm.auto.AbstractAutoRegistry;
import com.sugarcoat.protocol.dictionary.DictionaryManager;
import com.sugarcoat.support.dict.domain.SugarcoatDictionary;
import com.sugarcoat.support.dict.domain.SugarcoatDictionaryManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 字典自动注册处理
 *
 * @author 许向东
 * @date 2023/11/23
 */
public class DictionaryAutoRegistry extends AbstractAutoRegistry<SugarcoatDictionary> {

    private final DictionaryManager<SugarcoatDictionary> dictionaryManager;

    public DictionaryAutoRegistry(SugarcoatDictionaryManager dictionaryManager) {
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
                if (StrUtil.equals(scan.getDictGroup(), db.getDictGroup()) &&
                        StrUtil.equals(scan.getDictCode(), db.getDictCode())) {
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
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("com.sugarcoat.support.dict", InnerDictionary.class);
        List<SugarcoatDictionary> sugarcoatDictionaries = new ArrayList<>();
        for (Class<?> clazz : classes) {
            InnerDictionary innerDictionary = clazz.getAnnotation(InnerDictionary.class);
            String group;
            if (innerDictionary.value() == null || innerDictionary.value().isEmpty()) {
                group = clazz.getName();
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
                Map<Enum, Field> objectObjectHashMap = new HashMap<>();

                for (Object enumConstant : enumConstants) {
                    if (enumConstant instanceof Enum e) {
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
