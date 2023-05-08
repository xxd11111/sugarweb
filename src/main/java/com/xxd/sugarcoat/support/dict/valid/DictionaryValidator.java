package com.xxd.sugarcoat.support.dict.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/6
 */
public class DictionaryValidator implements ConstraintValidator<Dictionary, Serializable> {

    @Override
    public boolean isValid(Serializable serializable, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
