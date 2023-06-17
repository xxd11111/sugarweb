package com.sugarcoat.api.email;

import lombok.Data;

import java.util.List;

/**
 * 邮件传输对象
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/9
 */
@Data
public class EmailDTO {

	private String to;

	private String from;

	private String subject;

	private String text;

	private List<String> attachment;

	private List<String> inline;

}
