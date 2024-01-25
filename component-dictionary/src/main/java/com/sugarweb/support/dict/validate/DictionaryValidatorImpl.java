package com.sugarweb.support.dict.validate;

import com.sugarweb.BeanUtil;
import com.sugarweb.support.dict.api.DictionaryValidate;
import com.sugarweb.support.dict.api.DictionaryValidator;
import com.sugarweb.support.dict.application.DictionaryService;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 枚举校验工具
 *
 * @author xxd
 * @version 1.0
 */
public class DictionaryValidatorImpl implements DictionaryValidator {

    private String groupCode;

    public DictionaryService getDictionaryService() {
        return InnerDictionaryService.dictionaryService;
    }

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
        boolean isPresent = getDictionaryService().exist(groupCode, value);
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

    private static class InnerDictionaryService{
        private static final DictionaryService dictionaryService = BeanUtil.getBean(DictionaryService.class);
    }

}
