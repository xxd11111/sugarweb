package com.sugarcoat.support.dict.domain;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.dict.DictionaryGroup;
import com.sugarcoat.api.dict.InnerDictionary;
import com.sugarcoat.api.dict.InnerDictionaryGroup;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字典扫描
 *
 * @author xxd
 * @date 2023/8/30 23:03
 */
public class DefaultDictionaryScanner implements DictionaryScanner{
    @Override
    public List<SugarcoatDictionaryGroup> scan() {
        String scanPackage = "";
        //无效路径时处理
        if (!packageValidate(scanPackage)){
            //获取当前应用启动路径
            //or 抛出异常
        }
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(scanPackage, InnerDictionaryGroup.class);
        List<SugarcoatDictionaryGroup> dictGroups = new ArrayList<>();
        for (Class<?> clazz : classes) {
            InnerDictionaryGroup annotation = clazz.getAnnotation(InnerDictionaryGroup.class);
            String name;
            if (StrUtil.isEmpty(annotation.code())) {
                String simpleName = clazz.getSimpleName();
                name = humpToUnderline(simpleName).toUpperCase();
            } else {
                name = annotation.code();
            }
            SugarcoatDictionaryGroup dictGroup = new SugarcoatDictionaryGroup();
            //todo 补全缺失设置
            dictGroup.setGroupCode(name);
            dictGroup.setGroupName(name);
            Object[] enumConstants = clazz.getEnumConstants();
            Field[] declaredFields = clazz.getDeclaredFields();
            List<SugarcoatDictionary> dicts = new ArrayList<>();
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
                SugarcoatDictionary dict = new SugarcoatDictionary();
                //todo 补全缺失设置
                dict.setName(dictName);
                dict.setCode(dictCode);
                dicts.add(dict);
            }
            dictGroup.setSugarcoatDictionaries(dicts);
            dictGroups.add(dictGroup);
        }
        return dictGroups;
    }

    private boolean packageValidate(String scanPackage) {
        //todo
        return true;
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
}
