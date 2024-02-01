package com.sugarweb.framework.orm;

import com.sugarweb.framework.utils.EnumUtil;
import com.sugarweb.framework.common.EnumValue;
import com.sugarweb.framework.exception.FrameworkException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 枚举convert
 *
 * @author 许向东
 * @version 1.0
 */
@Converter
@SuppressWarnings("unchecked")
public class EnumConvert<T extends EnumValue<K>, K extends Serializable> implements AttributeConverter<T, K> {

    private final Class<T> clazz;

    public EnumConvert() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            this.clazz = (Class<T>) actualTypeArguments[0];
        } else {
            throw new FrameworkException("枚举convert初始化异常:{}", genericSuperclass.getTypeName());
        }
    }

    @Override
    public K convertToDatabaseColumn(T attribute) {
        return attribute.getValue();
    }

    @Override
    public T convertToEntityAttribute(K dbData) {
        return EnumUtil.toBeanNull(dbData, clazz);
    }

}
