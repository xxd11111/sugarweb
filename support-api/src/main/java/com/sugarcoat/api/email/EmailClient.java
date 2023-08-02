package com.sugarcoat.api.email;

/**
 * 邮件操作客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/9
 */
public interface EmailClient {

	void sendEmail(EmailDto emailDto);

}
