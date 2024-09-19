package com.sugarweb.dict.validate;

import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.dict.application.DictService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 枚举校验
 *
 * @author xxd
 * @version 1.0
 */
public class DictionaryValidator implements ConstraintValidator<DictionaryValidate, String> {

    private String groupCode;

    public DictService getDictionaryService() {
        return InnerDictionaryService.DICTIONARY_SERVICE;
    }

    public void initialize(DictionaryValidate annotation) {
        groupCode = annotation.groupCode();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 校验通过
        if (groupCode == null || groupCode.isEmpty()) {
            return true;
        }
        boolean isPresent = getDictionaryService().existsItemByCode(groupCode, value, null);
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
        private static final DictService DICTIONARY_SERVICE = BeanUtil.getBean(DictService.class);
    }

}
