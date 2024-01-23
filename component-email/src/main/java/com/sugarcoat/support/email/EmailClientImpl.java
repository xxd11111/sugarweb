package com.sugarcoat.support.email;

import com.google.common.collect.Iterables;
import com.sugarcoat.support.oss.api.FileInfo;
import com.sugarcoat.support.oss.api.FileClient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.InputStream;
import java.util.List;

/**
 * 邮件客户端
 *
 * @author xxd
 * @version 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
@Slf4j
public class EmailClientImpl implements EmailClient {

    private final JavaMailSender mailSender;

    private final FileClient fileClient;

    @Override
    public void sendEmail(Email email) {
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
            List<String> attachment = email.getAttachment();
            if (!Iterables.isEmpty(attachment)) {
                for (String id : attachment) {
                    FileInfo fileObject = fileClient.getFileObject(id);
                    String filename = fileObject.getFilename();
                    InputStream content = fileClient.getContent(id);
                    messageHelper.addAttachment(filename, new InputStreamResource(content));
                }
            }

            // 传入图片
            List<String> inlines = email.getInlines();
            if (!Iterables.isEmpty(inlines)) {
                for (String id : inlines) {
                    FileInfo fileObject = fileClient.getFileObject(id);
                    String contentType = fileObject.getContentType();
                    InputStream content = fileClient.getContent(id);
                    messageHelper.addInline(id, new InputStreamResource(content), contentType);
                }
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送异常", e);
        }
    }

}
