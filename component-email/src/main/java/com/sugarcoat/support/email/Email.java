package com.sugarcoat.support.email;

import java.util.List;

/**
 * 邮件传输信息
 *
 * @author xxd
 * @version 1.0
 * @version 1.0
 */
public interface Email {

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
