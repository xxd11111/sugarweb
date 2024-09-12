package com.sugarweb.email.application;

import lombok.Data;

import java.util.List;

/**
 * 邮件传输对象
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class EmailDto {

	/**
	 * 收件邮箱
	 */
	private String to;

	/**
	 * 发件邮箱
	 */
	private String from;

	/**
	 * 标题
	 */
	private String subject;

	/**
	 * 正文
	 */
	private String text;

	/**
	 * 附件
	 */
	private List<EmailAttachment> attachment;

	/**
	 * 插图
	 */
	private List<EmailInline> inlines;

}
