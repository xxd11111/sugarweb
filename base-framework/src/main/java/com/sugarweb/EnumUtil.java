package com.sugarweb;

import com.sugarweb.common.EnumValue;
import com.sugarweb.exception.ValidateException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 枚举工具类
 *
 * @author xxd
 * @version 1.0
 */
public class EnumUtil {

    public static <T extends Serializable, K extends EnumValue<T>> Optional<K> toBean(T value, Class<K> clazz) {
        K[] enumValues = clazz.getEnumConstants();
        return Arrays.stream(enumValues).filter(enumValue -> Objects.equals(enumValue.getValue(), value)).findFirst();
    }

    public static <T extends Serializable, K extends EnumValue<T>> K toBeanNull(T value, Class<K> clazz) {
        K[] enumValues = clazz.getEnumConstants();
        return Arrays.stream(enumValues)
                .filter(enumValue -> Objects.equals(enumValue.getValue(), value))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据枚举编码 enum获取枚举值，禁止空
     *
     * @param clazz 枚举class对象
     * @param value 枚举值
     * @param <T>   枚举编码对象类型
     * @param <K>   实现了EnumValue接口的对象
     */
    public static <T extends Serializable, K extends EnumValue<T>> K toBeanNonnull(T value, Class<K> clazz) {
        return toBean(value, clazz)
                .orElseThrow(() -> new ValidateException("未找到该枚举,value:{},enum:{}", value, clazz.getName()));
    }

}
