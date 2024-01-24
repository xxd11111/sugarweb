package com.sugarweb.support.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定时任务配置
 *
 * @author xxd
 * @version 1.0
 */
@ConfigurationProperties(prefix = "sugarcoat.scheduler")
@ConditionalOnProperty(prefix = "sugarcoat.scheduler", name = "enable", havingValue = "true")
public class SchedulerProperties {
}
