package com.xxd.sugarcoat.support.enums;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-14
 */
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = EnumValidator.class
)
public @interface InEnum {
    /**
     * @return 实现 EnumValuable 接口的
     */
    Class<? extends EnumValue<?>> value();

    String message() default "{fieldName}必须在指定范围 {enumCodes}";

    String fieldName() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
