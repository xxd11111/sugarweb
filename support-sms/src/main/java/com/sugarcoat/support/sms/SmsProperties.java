package com.sugarcoat.support.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * sms属性
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/8
 */
@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

	private Boolean enabled;

	private String type;

	private TencentSmsProperties tencentSmsProperties;

	private AliyunSmsProperties aliyunSmsProperties;

}
