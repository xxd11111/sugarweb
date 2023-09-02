package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.BeanUtil;
import com.sugarcoat.api.dict.DictionaryGroup;
import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.api.dict.DictionaryValidate;
import com.sugarcoat.api.dict.DictionaryValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

/**
 * 枚举校验工具
 *
 * @author xxd
 * @since 2022-11-14
 */
public class DictionaryValidatorImpl implements DictionaryValidator {

    private String groupCode;

    @Override
    public void initialize(DictionaryValidate annotation) {
        groupCode = annotation.groupCode();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 校验通过
        if (groupCode == null || groupCode.isEmpty()) {
            return true;
        }
        DictionaryManager dictionaryManager = BeanUtil.getBean(DictionaryManager.class);
        boolean isPresent = dictionaryManager.existDictionary(groupCode, value);
        if (isPresent) {
            //校验通过
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }

}
