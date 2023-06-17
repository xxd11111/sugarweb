package com.sugarcoat.dict.valid;

import com.sugarcoat.api.dict.DictionaryValidate;
import com.sugarcoat.api.dict.DictHelper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
		if (groupCode == null || groupCode.isEmpty() || value == null || value.isEmpty()) {
			return false;
		}
		return DictHelper.existDictionary(groupCode, value);
	}

}
