package com.sugarcoat.api.dict;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 字典校验注解
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/29
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
        validatedBy = DictionaryValidator.class
)
public @interface DictionaryValidate {

    /**
     * 字典组code
     */
    String groupCode();

    /**
     * 异常时返回的异常消息
     */
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
