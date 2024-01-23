package com.sugarcoat.support.dict.validate;

import com.sugarcoat.support.dict.api.DictionaryManager;
import com.sugarcoat.support.dict.api.DictionaryValidate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

/**
 * 字典校验
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
public class DictionaryValidator implements ConstraintValidator<DictionaryValidate, String> {

	private String groupCode;

	@NotEmpty
	private final DictionaryManager dictionaryManager;

	@Override
	public void initialize(DictionaryValidate constraintAnnotation) {
		groupCode = constraintAnnotation.groupCode();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (groupCode == null || groupCode.isEmpty() || value == null || value.isEmpty()) {
			return false;
		}
		return dictionaryManager.exist(groupCode, value);
	}

}
