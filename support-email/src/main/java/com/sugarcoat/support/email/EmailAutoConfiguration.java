package com.sugarcoat.support.email;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 邮件自动配置
 *
 * @author xxd
 * @since 2023/8/10
 */
@EntityScan
//@EnableJpaRepositories
//@EnableConfigurationProperties(EmailProperties.class)
public class EmailAutoConfiguration {


    @Bean(name = "javaMailSender")
    public JavaMailSender createMailSender() {
        //创建一个 JavaMailSenderImpl 的实例，用于发送邮件。
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        //设置邮件服务器的主机地址为 smtp.qq.com。这里假设您使用的是腾讯 QQ 邮箱服务。
        mailSender.setHost("smtp.qq.com");
        //设置邮件服务器的端口号为 465。
        mailSender.setPort(465);
        ///设置发件人的邮箱地址，即您 QQ 邮箱的地址。
        mailSender.setUsername("your_username@qq.com");
        //设置发件人的邮箱密码，即您 QQ 邮箱的登录密码。注意要妥善保护您的密码，不要将其泄露给他人。
        mailSender.setPassword("your_password");
        //创建一个名为 props 的 Properties 对象，用于保存邮件发送的相关配置信息。
        Properties props = mailSender.getJavaMailProperties();
        //将邮件的传输协议设置为 SMTPS（即带有 SSL 加密的 SMTP 协议）。
        props.put("mail.transport.protocol", "smtps");
        //表示启用 SSL 加密功能，保障邮件内容的安全。
        props.put("mail.smtp.ssl.enable", "true");
        //：将上面创建的 props 对象设置到 JavaMailSenderImpl 实例中，以便能够进行加密传输。
        mailSender.setJavaMailProperties(props);
        //返回 JavaMailSenderImpl 的实例，作为 Bean 对象。其他组件可以通过依赖注入来获取这个实例，进行邮件发送等操作。
        return mailSender;
    }

}
