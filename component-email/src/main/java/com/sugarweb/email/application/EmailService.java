package com.sugarweb.email.application;

import cn.hutool.core.collection.CollUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.InputStream;
import java.util.List;

/**
 * 邮件操作客户端
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final EmailFileService emailFileService;

    public void sendEmail(EmailDto email) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 收件人
            messageHelper.setTo(email.getTo());
            // 发件人
            messageHelper.setFrom(email.getFrom());
            // 标题
            messageHelper.setSubject(email.getSubject());
            // 发送html
            messageHelper.setText(email.getText(), true);
            // 附件
            List<EmailAttachment> attachment = email.getAttachment();
            if (CollUtil.isNotEmpty(attachment)) {
                for (EmailAttachment emailAttachment : attachment) {
                    InputStream content = emailFileService.getContent(emailAttachment.getFileId());
                    messageHelper.addAttachment(emailAttachment.getFilename(), new InputStreamResource(content));
                }
            }
            // 传入图片
            List<EmailInline> inlines = email.getInlines();
            if (CollUtil.isNotEmpty(inlines)) {
                for (EmailInline emailInline : inlines) {
                    InputStream content = emailFileService.getContent(emailInline.getFileId());
                    messageHelper.addInline(emailInline.getFilename(), new InputStreamResource(content));
                }
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送异常", e);
        }
    }
}
