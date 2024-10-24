package com.sugarweb.param;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参数配置
 *
 * @author xxd
 * @version 1.0
 */
@ConfigurationProperties(prefix = "sugarweb.param")
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
     * 扫描路径，扫描注解 @InnerParamScan
     */
    private String cachePrefix = "sugarweb:param:";


}
