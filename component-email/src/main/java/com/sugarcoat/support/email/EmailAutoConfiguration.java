package com.sugarcoat.support.email;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 邮件自动配置
 *
 * @author xxd
 * @version 1.0
 */
@EnableConfigurationProperties(EmailProperties.class)
@ConditionalOnProperty(prefix = "sugarcoat.email", name = "enable", havingValue = "true")
public class EmailAutoConfiguration {

    @Resource
    private EmailProperties emailProperties;

    @Bean
    public JavaMailSender createMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(emailProperties.getPassword());
        //创建一个名为 props 的 Properties 对象，用于保存邮件发送的相关配置信息。
        Properties props = mailSender.getJavaMailProperties();
        //将邮件的传输协议设置为 SMTPS（即带有 SSL 加密的 SMTP 协议）。
        props.put("mail.transport.protocol", "smtps");
        //表示启用 SSL 加密功能，保障邮件内容的安全。
        props.put("mail.smtp.ssl.enable", "true");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }

}
