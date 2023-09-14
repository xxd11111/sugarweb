package com.sugarcoat.support.sms;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * sms属性
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/8
 */
@ConfigurationProperties(prefix = "sugarcoat.sms")
@ConditionalOnProperty(prefix = "sugarcoat.sms", name = "enable", havingValue = "true")
@Data
public class SmsProperties {

	private String type;

	private TencentSmsProperties tencentSmsProperties;

	private AliyunSmsProperties aliyunSmsProperties;

}
