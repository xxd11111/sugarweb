package com.sugarcoat.support.dict.domain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.api.dict.InnerDictionary;
import com.sugarcoat.api.dict.InnerDictionaryGroup;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字典对象扫描
 *
 * @author xxd
 * @since 2023/8/25
 */
@RequiredArgsConstructor
public class DictScanner {

    private final DictionaryManager<SugarcoatDictionaryGroup, SugarcoatDictionary> dictionaryManager;

    public void register(List<DictGroup> dictGroups) {

        //todo
        List<SugarcoatDictionaryGroup> dbList = new ArrayList<>();
        List<SugarcoatDictionaryGroup> adddictionaryGroups = new ArrayList<>();
        List<String> removeIds = new ArrayList<>();
        for (DictGroup dictGroup : dictGroups) {
            boolean groupExist = false;
            for (SugarcoatDictionaryGroup SugarcoatDictionaryGroup : dbList) {
                if (StrUtil.equals(SugarcoatDictionaryGroup.getGroupCode(), "DICT_TYPE")) {
                    if (StrUtil.equals(SugarcoatDictionaryGroup.getGroupCode(), dictGroup.getCode())) {
                        groupExist = true;
                    }
                }
            }
            if (!groupExist) {
                //新增字典组
                SugarcoatDictionaryGroup dictionaryGroup = new SugarcoatDictionaryGroup();
                adddictionaryGroups.add(dictionaryGroup);
            }

            List<Dict> dicts = dictGroup.getDicts();
            int i = 1;
            //系统内字典
            for (Dict dict : dicts) {
                boolean dictExist = false;
                //数据库字典
                for (SugarcoatDictionaryGroup SugarcoatDictionaryGroup : dbList) {
                    if (StrUtil.equals(SugarcoatDictionaryGroup.getGroupCode(), dictGroup.getCode())) {
                        if (StrUtil.equals(dict.getCode(), SugarcoatDictionaryGroup.getGroupCode())) {
                            dictExist = true;
                        }
                    }
                }

                if (!groupExist || !dictExist) {
                    SugarcoatDictionaryGroup SugarcoatDictionaryGroup = new SugarcoatDictionaryGroup();

                    adddictionaryGroups.add(SugarcoatDictionaryGroup);
                }
            }
            if (CollUtil.isNotEmpty(dbList)) {
                AtomicBoolean exist = new AtomicBoolean(false);
                //数据库列表
                dbList.forEach((dictionaryGroup) -> {
                    //代码中某组字典
                    if (StrUtil.equals(dictGroup.getCode(), dictionaryGroup.getGroupCode())) {
                        for (Dict dict : dicts) {
                            if (StrUtil.equals(dict.getCode(), dictionaryGroup.getGroupCode())) {
                                exist.set(true);
                            }
                        }
                        if (!exist.get()) {
                            removeIds.add(dictionaryGroup.getGroupCode());
                        }
                    }
                });
            }
        }
        //for (SugarcoatDictionaryGroup dictionaryGroup : adddictionaryGroups) {
        //    dictService.addDict(dictionaryGroup);
        //}
        //dictService.removeByIds(removeIds);
        System.out.println("");
    }

    public List<DictGroup> scan() {
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("com.thinvent.catalog", InnerDictionaryGroup.class);
        List<DictGroup> dictGroups = new ArrayList<>();
        for (Class<?> clazz : classes) {
            InnerDictionaryGroup annotation = clazz.getAnnotation(InnerDictionaryGroup.class);
            String name;
            if (StrUtil.isEmpty(annotation.code())) {
                String simpleName = clazz.getSimpleName();
                name = humpToUnderline(simpleName).toUpperCase();
            } else {
                name = annotation.code();
            }
            DictGroup dictGroup = new DictGroup();
            dictGroup.setCode(name);
            dictGroup.setName(name);
            Object[] enumConstants = clazz.getEnumConstants();
            Field[] declaredFields = clazz.getDeclaredFields();
            List<Dict> dicts = new ArrayList<>();
            for (Object enumConstant : enumConstants) {
                String dictCode = "";
                String dictName = "";
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    Annotation dictCode1 = field.getAnnotation(InnerDictionary.class);
                    try {
                        if (dictCode1 != null) {
                            dictCode = (String) field.get(enumConstant);
                        }
                        if (dictCode1 != null) {
                            dictName = (String) field.get(enumConstant);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
                if (dictCode == null || dictCode.isEmpty()) {
                    dictCode = enumConstant.toString();
                }
                if (dictName == null || dictName.isEmpty()) {
                    dictName = enumConstant.toString();
                }
                Dict dict = new Dict();
                dict.setName(dictName);
                dict.setCode(dictCode);
                dicts.add(dict);
            }
            dictGroup.setDicts(dicts);
            dictGroups.add(dictGroup);
        }
        return dictGroups;
    }


    private static final Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");

    public static String underlineToHump(String str) {
        //正则匹配下划线及后一个字符，删除下划线并将匹配的字符转成大写
        Matcher matcher = UNDERLINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配的子串替换成指定字符串，并且将替换后的子串及之前到上次匹配的子串之后的字符串添加到StringBuffer对象中
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            //把之后的字符串也添加到StringBuffer对象中
            matcher.appendTail(sb);
        } else {
            //去除除字母之外的前面带的下划线
            return sb.toString().replaceAll("_", "");
        }
        return underlineToHump(sb.toString());
    }

    /**
     * @param str 字符串
     * @return java.lang.String
     * @Description 将驼峰转为下划线
     */
    public static String humpToUnderline(String str) {
        //匹配 A-Z
        Pattern compile = Pattern.compile("[A-Z]");

        //进行匹配，结果存入 匹配器
        Matcher matcher = compile.matcher(str);

        StringBuffer sb = new StringBuffer();

        //如果匹配中存在
        while (matcher.find()) {
            //加入下换线，并且转为 小写。

            //如果是首字符，这里 应该直接转为小写。比如截取 字符串的第一个，判断是不是 [A-Z] 之间的
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        //添加到 sb中
        matcher.appendTail(sb);
        return sb.toString().replaceFirst("_", "");
    }

    @Data
    class DictGroup {
        private String code;
        private String name;

        private List<Dict> dicts;
    }

    @Data
    class Dict {
        private String code;
        private String name;
    }

}
