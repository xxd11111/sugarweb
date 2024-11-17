package com.sugarweb.digitalHuman.config;

import com.sugarweb.digitalHuman.constants.Common;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BlblClientProperties
 *
 * @author xxd
 * @since 2024/10/19 23:47
 */
@Data
@ConfigurationProperties(prefix = Common.CONFIG_PREFIX + ".blbl-client")
public class BlblClientProperties {

    private Integer roomId;

    private String cookie;

    private Integer selfUid;

}
