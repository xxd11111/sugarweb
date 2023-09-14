package com.sugarcoat.api.email;

import java.util.List;

/**
 * 邮件传输信息
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/9
 */
public interface EmailInfo {

	/**
	 * 发件邮箱
	 */
	String getFrom();

	/**
	 * 收件邮箱
	 */
	String getTo();

	/**
	 * 标题
	 */
	String getSubject();

	/**
	 * 正文
	 */
	String getText();

	/**
	 * 附件
	 */
	List<String> getAttachment();

	/**
	 * 插图
	 */
	List<String> getInlines();

}
