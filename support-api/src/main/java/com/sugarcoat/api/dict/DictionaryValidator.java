package com.sugarcoat.api.dict;

import jakarta.validation.ConstraintValidator;

/**
 * 枚举校验工具
 *
 * @author xxd
 * @date 2022-11-14
 */
public interface DictionaryValidator extends ConstraintValidator<DictionaryValidate, String> {

}
