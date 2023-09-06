package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.api.dict.DictionaryValidate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

/**
 * 字典校验
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/6
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
		return dictionaryManager.existDictionary(groupCode, value);
	}

}
