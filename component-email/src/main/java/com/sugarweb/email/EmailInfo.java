package com.sugarweb.email;

import lombok.Data;

import java.util.List;

/**
 * 邮件传输对象
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class EmailInfo {

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
	private List<String> attachment;

	/**
	 * 插图
	 */
	private List<String> inlines;

}
