package com.sugarcoat.support.dict.domain;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.support.orm.BooleanEnum;
import com.sugarcoat.protocol.dict.InnerDictionary;
import com.sugarcoat.protocol.dict.InnerDictionaryGroup;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.support.dict.DictionaryProperties;

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
 * @since 2023/8/30 23:03
 */
public class DefaultDictionaryScanner implements DictionaryScanner {

    /**
     * 扫描路径 @InnerDictionary
     */
    private final String scanPackage;

    public DefaultDictionaryScanner(DictionaryProperties properties) {
        String scanPackage = properties.getScanPackage();
        if (StrUtil.isEmpty(scanPackage)) {
            throw new FrameworkException("not setting properties dictionary.scanPackage!");
        }
        this.scanPackage = scanPackage;
    }

    @Override
    public List<SugarcoatDictionaryGroup> scan() {
        //获取包下class
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
            //字典组对象创建
            SugarcoatDictionaryGroup dictGroup = new SugarcoatDictionaryGroup();
            dictGroup.setInnerFlag(BooleanEnum.TRUE);
            dictGroup.setGroupCode(name);
            dictGroup.setGroupName(name);
            //解析枚举获取field信息
            List<SugarcoatDictionary> dicts = new ArrayList<>();
            Object[] enumConstants = clazz.getEnumConstants();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Object enumConstant : enumConstants) {
                String dictCode = "";
                String dictName = "";
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    Annotation enumDictCode = field.getAnnotation(InnerDictionary.class);
                    try {
                        //获取字典编码
                        if (enumDictCode != null) {
                            dictCode = (String) field.get(enumConstant);
                        }
                        //获取字典名称
                        if (enumDictCode != null) {
                            dictName = (String) field.get(enumConstant);
                        }
                    } catch (IllegalAccessException e) {
                        throw new FrameworkException(StrUtil.format("字典项数据获取异常,请检查字典枚举配置，class:{}", clazz.getName()));
                    }

                }
                //缺省设置，字典编码缺失抛异常
                if (dictCode == null || dictCode.isEmpty()) {
                    throw new FrameworkException(StrUtil.format("字典编码缺失,请检查字典枚举配置，class:{}", clazz.getName()));
                }
                //缺省设置，字典编码缺失抛异常
                if (dictName == null || dictName.isEmpty()) {
                    dictName = enumConstant.toString();
                }
                //字典项对象创建
                SugarcoatDictionary dict = new SugarcoatDictionary();
                dict.setName(dictName);
                dict.setCode(dictCode);
                dicts.add(dict);
            }
            dictGroup.setSugarcoatDictionaries(dicts);
            dictGroups.add(dictGroup);
        }
        return dictGroups;
    }

    private static final Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");

//    private static String underlineToHump(String str) {
//        //正则匹配下划线及后一个字符，删除下划线并将匹配的字符转成大写
//        Matcher matcher = UNDERLINE_PATTERN.matcher(str);
//        StringBuffer sb = new StringBuffer(str);
//        if (matcher.find()) {
//            sb = new StringBuffer();
//            //将当前匹配的子串替换成指定字符串，并且将替换后的子串及之前到上次匹配的子串之后的字符串添加到StringBuffer对象中
//            //正则之前的字符和被替换的字符
//            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
//            //把之后的字符串也添加到StringBuffer对象中
//            matcher.appendTail(sb);
//        } else {
//            //去除除字母之外的前面带的下划线
//            return sb.toString().replaceAll("_", "");
//        }
//        return underlineToHump(sb.toString());
//    }

    /**
     * 将驼峰转为下划线
     *
     * @param str 字符串
     * @return 下划线字符串
     */
    private static String humpToUnderline(String str) {
        //匹配 A-Z
        Pattern compile = Pattern.compile("[A-Z]");

        //进行匹配，结果存入 匹配器
        Matcher matcher = compile.matcher(str);

        StringBuilder sb = new StringBuilder();

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
