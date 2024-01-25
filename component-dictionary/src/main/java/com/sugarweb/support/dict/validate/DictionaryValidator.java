package com.sugarweb.support.dict.validate;

import com.sugarweb.support.dict.api.DictionaryValidate;
import com.sugarweb.support.dict.application.DictionaryService;
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
	private final DictionaryService dictionaryService;

	@Override
	public void initialize(DictionaryValidate constraintAnnotation) {
		groupCode = constraintAnnotation.groupCode();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (groupCode == null || groupCode.isEmpty() || value == null || value.isEmpty()) {
			return false;
		}
		return dictionaryService.exist(groupCode, value);
	}

}
