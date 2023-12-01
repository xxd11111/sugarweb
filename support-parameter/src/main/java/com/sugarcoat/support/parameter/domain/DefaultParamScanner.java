package com.sugarcoat.support.parameter.domain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.parameter.ParameterCode;
import com.sugarcoat.protocol.parameter.InnerParameter;
import com.sugarcoat.support.parameter.ParamProperties;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 内置参数扫描
 *
 * @author xxd
 * @since 2023/9/5 22:14
 */
public class DefaultParamScanner implements ParamScanner {

    /**
     * 扫描路径
     */
    private final String scanPackage;

    public DefaultParamScanner(ParamProperties paramProperties) {
        this.scanPackage = paramProperties.getScanPackage();
    }

    @Override
    public Collection<SugarcoatParameter> scan() {
        //获取包下class
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(scanPackage, InnerParameter.class);
        List<SugarcoatParameter> dictGroups = new ArrayList<>();
        if (CollUtil.isEmpty(classes)) {
            return dictGroups;
        }
        //遍历class
        Collection<SugarcoatParameter> sugarcoatParams = new ArrayList<>();

        for (Class<?> clazz : classes) {
            Field[] declaredFields = clazz.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(declaredFields)) {
                for (Field field : declaredFields) {
                    ParameterCode annotation = field.getAnnotation(ParameterCode.class);
                    String code = annotation.code();
                    String name = annotation.name();
                    String value = annotation.value();

                    if (StrUtil.isEmpty(value)) {
                        throw new FrameworkException("未设置默认值，请检查：{}", clazz.getSimpleName());
                    }
                    //为空时候取字段名称
                    if (StrUtil.isEmpty(code)) {
                        code = humpToUnderline(field.getName());
                    }
                    //为空时候取字段名称
                    if (StrUtil.isEmpty(name)) {
                        value = humpToUnderline(field.getName());
                    }
                    //创建参数对象
                    SugarcoatParameter sugarcoatParam = new SugarcoatParameter();
                    // sugarcoatParam.setId();
                    sugarcoatParam.setCode(code);
                    sugarcoatParam.setName(name);
                    sugarcoatParam.setValue(value);
                    sugarcoatParam.setDefaultValue(value);
                    sugarcoatParam.setComment(name);
                    sugarcoatParams.add(sugarcoatParam);
                }
            }
        }
        return sugarcoatParams;
    }

    /**
     * 将驼峰转为下划线
     *
     * @param str 字符串
     * @return 下划线字符串
     */
    public static String humpToUnderline(String str) {
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
