package com.sugarcoat.support.parameter;

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
     * 是否注册系统内置参数
     */
    private boolean enableRegister = false;

    /**
     * 扫描路径
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
