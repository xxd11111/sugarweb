package com.sugarcoat.api.sms;

import com.sugarcoat.api.SpringUtil;

/**
 * sms帮助文档
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/8
 */
public class SmsHelper {

	private static SMSClient getInstance() {
		return SmsHelperInner.SMS_CLIENT;
	}

	public static void sendMessage(MessageTemplate messageTemplate, String[] phoneNumbers, String[] params) {
		getInstance().sendMessage(messageTemplate.getTemplateId(), phoneNumbers, params);
	}

	public static void sendMessage(MessageTemplate messageTemplate, String phoneNumber, String[] params) {
		getInstance().sendMessage(messageTemplate.getTemplateId(), phoneNumber, params);
	}

	private static class SmsHelperInner {

		private static final SMSClient SMS_CLIENT = SpringUtil.getBean(SMSClient.class);

	}

}
