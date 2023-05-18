package com.sugarcoat.protocol.enums;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author xxd
 * @description 枚举校验工具
 * @date 2022-11-14
 */
public class EnumValidator implements ConstraintValidator<InEnum, Serializable> {
    private final Collection<Serializable> enumCodes = new ArrayList<>();
    private String fieldName;

    @Override
    public void initialize(InEnum annotation) {
        this.fieldName = annotation.fieldName();
        EnumValue<?>[] values = annotation.value().getEnumConstants();
        if (values.length > 0) {
            for (EnumValue<?> value : values) {
                enumCodes.add(value.getCode());
            }
        }
    }

    @Override
    public boolean isValid(Serializable value, ConstraintValidatorContext context) {
        // 为空时，默认不校验，即认为通过
        if (value == null) {
            return true;
        }
        // 校验通过
        if (enumCodes.contains(value)) {
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replace("{fieldName}", fieldName)
                .replace("{enumCodes}", enumCodes.toString())).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }

}
