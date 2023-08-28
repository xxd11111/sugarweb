package com.sugarcoat.api.dict;

import com.sugarcoat.api.BeanUtil;
import com.sugarcoat.api.common.Flag;
import com.sugarcoat.api.data.EnumValidate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 枚举校验工具
 *
 * @author xxd
 * @date 2022-11-14
 */
public class DictionaryValidator implements ConstraintValidator<DictionaryValidate, String> {

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
        DictionaryManager<DictionaryGroup<Dictionary>, Dictionary> dictionaryManager = BeanUtil.getBean(DictionaryManager.class);
        Collection<Dictionary> dictionaries = dictionaryManager.getDictionary(groupCode);
        if (dictionaries != null && !dictionaries.isEmpty()) {
            boolean flag = dictionaries.stream().anyMatch(a -> groupCode.equals(a.getCode()));
            //校验通过
            if (flag) {
                return true;
            }
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        context.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }

}
