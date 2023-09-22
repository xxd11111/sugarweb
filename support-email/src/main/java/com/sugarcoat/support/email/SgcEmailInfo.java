package com.sugarcoat.support.email;

import com.sugarcoat.protocol.email.EmailInfo;
import lombok.Data;

import java.util.List;

/**
 * 邮件传输对象
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/9
 */
@Data
public class SgcEmailInfo implements EmailInfo {

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
