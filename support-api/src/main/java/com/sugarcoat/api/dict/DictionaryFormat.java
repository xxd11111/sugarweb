package com.sugarcoat.api.dict;

import java.lang.annotation.*;

/**
 * 字典格式化，使用在字典编码字段上，将字典编码值对应的名称放在mappingFiled上返回给前端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/30
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictionaryFormat {

    /**
     * 映射字段
     */
    String mappingFiled();

}
