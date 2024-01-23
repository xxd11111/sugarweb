package com.xxd.common;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 枚举校验
 *
 * @author xxd
 * @since 2022-11-14
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValidate {

    /**
     * @return 实现 EnumValuable 接口的
     */
    Class<? extends EnumValue<?>> value();

    String message() default "{fieldName}必须在指定范围 {enumCodes}";

    String fieldName() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
