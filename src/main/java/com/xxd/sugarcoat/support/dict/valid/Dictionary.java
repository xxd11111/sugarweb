package com.xxd.sugarcoat.support.dict.valid;

import com.xxd.sugarcoat.support.enums.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/6
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
public @interface Dictionary {

    /**
     * 字典组code
     */
    String value();

    /**
     * 集成了字典功能的class
     */
    Class<?> dictGroupClass();

    String message() default "{fieldName}必须在指定范围 {codes}";

    String fieldName() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
