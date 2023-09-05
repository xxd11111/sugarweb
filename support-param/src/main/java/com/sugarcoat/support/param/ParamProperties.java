package com.sugarcoat.support.param;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参数配置
 *
 * @author xxd
 * @since 2023/9/5 22:03
 */
@ConfigurationProperties(prefix = "sugarcoat.param")
@ConditionalOnProperty(prefix = "sugarcoat.param", name = "enable", havingValue = "true")
@Data
public class ParamProperties {
    /**
     * 扫描路径
     * todo 配置一个全局的
     */
    private String scanPackage;

    /**
     * 是否启用缓存 默认false
     */
    private boolean enableCache = false;

    /**
     * 扫描路径，扫描注解 @InnerParamScan
     */
    private String cachePrefix = "sugarcoat:param:";


}
