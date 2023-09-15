package com.sugarcoat.api.sms;

import java.util.Collection;

/**
 * sms客户端
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/8
 */
public interface SmsClient {

	void sendMessage(SmsInfo smsInfo);

	void addTemplate(SmsTemplateInfo smsTemplateInfo);

	void modifyTemplate(SmsTemplateInfo smsTemplateInfo);

	void deleteTemplate(String id);

	SmsTemplateInfo getTemplate(String id);

	Collection<SmsTemplateInfo> listTemplate(int pageIndex, int pageSize);

}
