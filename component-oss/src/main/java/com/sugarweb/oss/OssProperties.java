package com.sugarweb.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * oss配置类
 *
 * @author xxd
 * @version 1.0
 */
@ConfigurationProperties(prefix = "sugarweb.oss")
@Data
public class OssProperties {

    private String bucketName;

    private String endPoint;

    private String accessKey;

    private String secretKey;

}
