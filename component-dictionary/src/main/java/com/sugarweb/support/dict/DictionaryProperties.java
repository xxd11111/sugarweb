package com.sugarweb.support.dict;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 字典配置类
 *
 * @author xxd
 * @version 1.0
 */
@ConfigurationProperties(prefix = "sugarweb.dictionary")
@Data
public class DictionaryProperties {

    /**
     * 扫描路径，扫描注解 @InnerDictionary
     */
    private String scanPackage;

    /**
     * 是否启用缓存 默认false
     */
    private boolean enableCache = false;

    /**
     * 扫描路径，扫描注解 @InnerDictionary
     */
    private String cacheprefix = "sugarweb:dict:";


}
