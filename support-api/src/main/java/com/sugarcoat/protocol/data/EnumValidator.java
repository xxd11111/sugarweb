package com.sugarcoat.protocol.data;

import com.sugarcoat.protocol.common.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 枚举校验工具
 *
 * @author xxd
 * @since 2022-11-14
 */
public class EnumValidator implements ConstraintValidator<EnumValidate, String> {

    private final Collection<Serializable> enumCodes = new ArrayList<>();

    private String fieldName;

    @Override
    public void initialize(EnumValidate annotation) {
        this.fieldName = annotation.fieldName();
        EnumValue<?>[] values = annotation.value().getEnumConstants();
        for (EnumValue<?> value : values) {
            enumCodes.add(value.getValue());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 校验通过
        if (enumCodes.contains(value)) {
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                        .replace("{fieldName}", fieldName).replace("{enumCodes}", enumCodes.toString()))
                .addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }

}
