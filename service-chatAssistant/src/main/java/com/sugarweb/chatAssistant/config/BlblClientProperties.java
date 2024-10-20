package com.sugarweb.chatAssistant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BlblClientProperties
 *
 * @author xxd
 * @since 2024/10/19 23:47
 */
@Data
@ConfigurationProperties(prefix = "sugarweb.chat-assistant.blbl-client")
public class BlblClientProperties {

    private Integer roomId;

    private String cookie;

    private Integer selfUid;

}
