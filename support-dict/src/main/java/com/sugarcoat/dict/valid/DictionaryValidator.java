package com.sugarcoat.dict.valid;

import com.sugarcoat.dict.api.DictionaryGroup;
import com.sugarcoat.dict.api.DictionaryValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 字典校验
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/6
 */
public class DictionaryValidator implements ConstraintValidator<DictionaryValidate, String> {
    private DictionaryGroup dictionaryGroup;

    @Override
    public void initialize(DictionaryValidate constraintAnnotation) {
        String groupCode = constraintAnnotation.dictionaryGroupCode();
        dictionaryGroup = null;
    }

    @Override
    public boolean isValid(String serializable, ConstraintValidatorContext constraintValidatorContext) {
        if (dictionaryGroup == null) {
            return false;
        }
        return dictionaryGroup.existDictionary(serializable);
    }
}
