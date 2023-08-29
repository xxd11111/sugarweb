package com.sugarcoat.support.dict;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 字典配置类
 *
 * @author xxd
 * @date 2023/8/28 22:59
 */
@ConfigurationProperties(prefix = "sugarcoat.dictionary")
@ConditionalOnProperty(prefix = "sugarcoat.dictionary", name = "enable", havingValue = "true")
public class DictionaryProperties {

    /**
     * 扫描路径 @InnerDictionary
     */
    private String scanPackage;

    /**
     * 加载策略 关闭  只新增  智能合并（已修改过的不更改）  覆盖
     */
    private String registerStrategy;

    /**
     * 是否启用缓存 默认false
     */
    private boolean enableCache = false;



}
