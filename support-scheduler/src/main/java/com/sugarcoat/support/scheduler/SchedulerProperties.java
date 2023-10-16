package com.sugarcoat.support.scheduler;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO
 *
 * @author xxd
 * @since 2023/10/16 20:48
 */
@ConfigurationProperties(prefix = "sugarcoat.scheduler")
@ConditionalOnProperty(prefix = "sugarcoat.scheduler", name = "enable", havingValue = "true")
@Data
public class SchedulerProperties {
}
