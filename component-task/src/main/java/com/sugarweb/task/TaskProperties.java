package com.sugarweb.task;

import com.sugarweb.task.infra.auto.AutoRegisterStrategy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${enable}")
    private boolean enable = true;

    @Value("${auto-register-strategy}")
    private AutoRegisterStrategy autoRegisterStrategy = AutoRegisterStrategy.save;

}
