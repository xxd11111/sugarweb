package com.sugarcoat.support.email;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 邮箱属性
 *
 * @author 许向东
 * @version 1.0
 */
@ConfigurationProperties(prefix = "sugarcoat.email")
@ConditionalOnProperty(prefix = "sugarcoat.email", name = "enable", havingValue = "true")
@Data
public class EmailProperties {

    private String host;

    private int port;

    private String username;

    private String password;

}
