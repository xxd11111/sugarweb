package com.sugarcoat.support.email;

import cn.hutool.core.collection.CollUtil;
import com.sugarcoat.api.email.EmailClient;
import com.sugarcoat.api.email.EmailInfo;
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
    public void sendEmail(EmailInfo emailInfo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 收件人
            messageHelper.setTo(emailInfo.getTo());
            // 发件人
            messageHelper.setFrom(emailInfo.getFrom());
            // 标题
            messageHelper.setSubject(emailInfo.getSubject());
            // 发送html
            messageHelper.setText(emailInfo.getText(), true);
            // 附件
            List<String> attachment = emailInfo.getAttachment();
            if (CollUtil.isNotEmpty(attachment)) {
                //todo 等oss
                for (String id : attachment) {
                    String filename = null;
                    MultipartFile file = null;
                    messageHelper.addAttachment(filename, file);
                }
            }

            // 传入图片
            List<String> inlines = emailInfo.getInlines();
            if (CollUtil.isNotEmpty(inlines)) {
                //todo 等oss
                for (String id : inlines) {
                    String contentType = null;
                    MultipartFile file = null;
                    messageHelper.addInline(id, file, contentType);
                }
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送异常", e);
        }
    }

}
