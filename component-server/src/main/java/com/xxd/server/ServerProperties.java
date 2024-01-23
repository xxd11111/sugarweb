package com.xxd.server;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ServerProperties
 *
 * @author xxd
 * @since 2023/10/8 21:53
 */
@ConfigurationProperties(prefix = "sugarcoat.server")
@ConditionalOnProperty(prefix = "sugarcoat.server", name = "enable", havingValue = "true")
@Data
public class ServerProperties {

}
