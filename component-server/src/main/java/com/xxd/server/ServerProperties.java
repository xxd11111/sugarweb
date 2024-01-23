package com.xxd.server;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ServerProperties
 *
 * @author xxd
 * @version 1.0
 */
@ConfigurationProperties(prefix = "sugarcoat.server")
@ConditionalOnProperty(prefix = "sugarcoat.server", name = "enable", havingValue = "true")
@Data
public class ServerProperties {

}
