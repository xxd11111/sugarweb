package com.sugarweb.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定时任务配置
 *
 * @author xxd
 * @version 1.0
 */
@ConfigurationProperties(prefix = "sugarweb.scheduler")
@ConditionalOnProperty(prefix = "sugarweb.scheduler", name = "enable", havingValue = "true")
public class SchedulerProperties {
}
