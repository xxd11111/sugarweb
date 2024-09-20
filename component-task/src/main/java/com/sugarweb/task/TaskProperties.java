package com.sugarweb.task;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定时任务配置
 *
 * @author xxd
 * @version 1.0
 */
@ConfigurationProperties(prefix = "sugarweb.task")
@Data
public class TaskProperties {

    private boolean enable = true;

}
