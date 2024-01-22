package com.sugarcoat.protocol.email;

/**
 * 邮件操作客户端
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/9
 */
public interface EmailClient {

	void sendEmail(Email email);

}
