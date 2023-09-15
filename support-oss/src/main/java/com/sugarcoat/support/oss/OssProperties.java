package com.sugarcoat.support.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * oss配置类
 *
 * @author 许向东
 * @date 2023/9/15
 */
@ConfigurationProperties(prefix = "sugarcoat.oss")
@Data
public class OssProperties {

    private String bucketName;

    private String endPoint;

    private String accessKey;

    private String secretKey;

}
