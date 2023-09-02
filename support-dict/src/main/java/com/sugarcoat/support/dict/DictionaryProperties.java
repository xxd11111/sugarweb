package com.sugarcoat.support.dict;

import com.sugarcoat.support.dict.domain.DictionaryRegistryStrategy;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 字典配置类
 *
 * @author xxd
 * @since 2023/8/28 22:59
 */
@ConfigurationProperties(prefix = "sugarcoat.dictionary")
@ConditionalOnProperty(prefix = "sugarcoat.dictionary", name = "enable", havingValue = "true")
@Data
public class DictionaryProperties {

    /**
     * 扫描路径，扫描注解 @InnerDictionary
     */
    private String scanPackage;

    /**
     * 加载策略:关闭，只新增，智能合并（已修改过的不更改），覆盖
     * 关闭（默认）
     */
    private String registerStrategy = DictionaryRegistryStrategy.DISABLE.name();

    /**
     * 是否启用缓存 默认false todo 未作适配
     */
    private boolean enableCache = false;

    /**
     * 扫描路径，扫描注解 @InnerDictionary
     */
    private String cachePrefix = "sugarcoat:dict:";


}
