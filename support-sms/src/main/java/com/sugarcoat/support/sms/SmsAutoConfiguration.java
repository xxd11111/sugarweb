package com.sugarcoat.support.sms;

import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.protocol.sms.SmsClient;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

/**
 * sms自动配置
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/8
 */
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(prefix = "sugarcoat.email", name = "enable", havingValue = "true")
public class SmsAutoConfiguration {

    @Resource
    private SmsProperties smsProperties;

    @Bean
    public SmsClient smsClient() {
        String type = smsProperties.getType();
        AliyunSmsProperties aliyunSmsProperties = smsProperties.getAliyunSmsProperties();
        if ("aliyun".equals(type) && Objects.nonNull(aliyunSmsProperties)) {
            return new AliyunSmsClient(aliyunSmsProperties);
        }
        TencentSmsProperties tencentSmsProperties = smsProperties.getTencentSmsProperties();
        if ("tencent".equals(type) && Objects.nonNull(tencentSmsProperties)){
            return new TencentSmsClient(tencentSmsProperties);
        }
        throw new FrameworkException("未配置sms信息");
    }

}
