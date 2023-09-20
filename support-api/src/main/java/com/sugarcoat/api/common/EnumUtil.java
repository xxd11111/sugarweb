package com.sugarcoat.api.common;

import com.sugarcoat.api.server.exception.ValidateException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author xxd
 * @description 枚举工具类
 * @since 2022-11-22
 */
public class EnumUtil {

    public static <T extends Serializable, K extends Flag<T>> Flag<T> getByCode(Class<K> clazz, T code) {
        K[] flags = clazz.getEnumConstants();
        return Arrays.stream(flags).filter(flag -> Objects.equals(flag.getCode(), code)).findFirst()
                .orElseThrow(() -> new ValidateException("枚举转换异常"));
    }

    public static <T extends Serializable, K extends Flag<T>> Flag<T> checkByCode(Class<K> clazz, T code) {
        return checkByCode(clazz, code, () -> new ValidateException("枚举转换异常"));
    }

    public static <T extends Serializable, E extends RuntimeException, K extends Flag<T>> Flag<T> checkByCode(
            Class<K> clazz, T code, Supplier<E> errorSupplier) {
        Flag<T> flag = getByCode(clazz, code);
        if (null == flag) {
            throw errorSupplier.get();
        }
        return flag;
    }

}
