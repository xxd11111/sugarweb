package com.sugarcoat.support.email;

import com.sugarcoat.api.email.EmailClient;
import com.sugarcoat.api.email.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 邮件客户端
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/9
 */
@RequiredArgsConstructor
@Slf4j
public class EmailClientImpl implements EmailClient {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(EmailDto emailDto) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            // 收件人
            messageHelper.setTo(emailDto.getTo());
            // 发件人
            messageHelper.setFrom(emailDto.getFrom());
            // 标题
            messageHelper.setSubject(emailDto.getSubject());
            // 发送html
            messageHelper.setText(emailDto.getText(), true);
            // 附件
            List<String> attachment = emailDto.getAttachment();
            for (String id : attachment) {
                String filename = null;
                MultipartFile file = null;
                messageHelper.addAttachment(filename, file);
            }
            List<String> inlines = emailDto.getInlines();
            for (String id : inlines) {
                String contentType = null;
                MultipartFile file = null;
                messageHelper.addInline(id, file, contentType);
            }
            // 传入图片
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送异常", e);
            e.printStackTrace();
        }
    }

}
