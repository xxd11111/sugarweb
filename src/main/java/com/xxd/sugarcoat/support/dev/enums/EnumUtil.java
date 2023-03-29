package com.xxd.sugarcoat.support.dev.enums;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.util.ArrayUtil;
import com.xxd.sugarcoat.support.dev.exception.ValidateException;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @author xxd
 * @description 枚举工具类
 * @date 2022-11-22
 */
public class EnumUtil {

    public static <T extends Serializable, K extends EnumValue<T>> EnumValue<T> getByCode(Class<K> clazz, T code) {
        K[] enumValues = clazz.getEnumConstants();
        return ArrayUtil.firstMatch(enumValue -> CompareUtil.compare(enumValue.getCode(), code, false) == 0, enumValues);
    }

    public static <T extends Serializable, K extends EnumValue<T>> EnumValue<T> checkByCode(Class<K> clazz, T code) {
        return checkByCode(clazz, code, () -> new ValidateException("枚举转换异常"));
    }

    public static <T extends Serializable, E extends RuntimeException, K extends EnumValue<T>> EnumValue<T> checkByCode(Class<K> clazz, T code, Supplier<E> errorSupplier) {
        EnumValue<T> enumValue = getByCode(clazz, code);
        if (null == enumValue) {
            throw errorSupplier.get();
        }
        return enumValue;
    }

}
