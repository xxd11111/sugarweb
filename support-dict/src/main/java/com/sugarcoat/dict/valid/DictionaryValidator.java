package com.sugarcoat.dict.valid;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.dict.api.DictHelper;
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
    private String groupCode;

    @Override
    public void initialize(DictionaryValidate constraintAnnotation) {
        groupCode = constraintAnnotation.groupCode();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isEmpty(groupCode) || StrUtil.isEmpty(value)) {
            return false;
        }
        return DictHelper.existDictionary(groupCode, value);
    }
}
