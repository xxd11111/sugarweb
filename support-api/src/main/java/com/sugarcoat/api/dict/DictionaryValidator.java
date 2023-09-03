package com.sugarcoat.api.dict;

import jakarta.validation.ConstraintValidator;

/**
 * 枚举校验接口
 *
 * @author xxd
 * @since 2022-11-14
 */
public interface DictionaryValidator extends ConstraintValidator<DictionaryValidate, String> {

}
