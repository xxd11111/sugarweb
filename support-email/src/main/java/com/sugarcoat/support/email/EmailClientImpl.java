package com.sugarcoat.support.email;

import com.sugarcoat.api.email.EmailClient;
import com.sugarcoat.api.email.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/9
 */
@RequiredArgsConstructor
public class EmailClientImpl implements EmailClient {

	private final JavaMailSenderImpl mailSender;

	@Override
	public void sendEmail(EmailDto emailDTO) {
		// todo file inputStream
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			// 附件
			File file = new File("C:\\code\\SpringDemo\\src\\main\\resources\\static\\test.txt");
			// 收件人
			messageHelper.setTo(emailDTO.getTo());
			// 发件人
			messageHelper.setFrom(emailDTO.getFrom());
			// 标题
			messageHelper.setSubject(emailDTO.getSubject());
			// 发送html
			messageHelper.setText(emailDTO.getText(), true);
			// 附件
			messageHelper.addAttachment("测试附件.txt", file);
			// 传入图片
			File pic = new File("C:\\code\\SpringDemo\\src\\main\\resources\\static\\d1.jpg");
			messageHelper.addInline("img", pic);
			mailSender.send(message);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
