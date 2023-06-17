package com.sugarcoat.api.sms;

/**
 * sms客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/8
 */
public interface SMSClient {

	void sendMessage(String templateId, String phoneNumber, String[] params);

	void sendMessage(String templateId, String[] phoneNumbers, String[] params);

	void addTemplate(String name, String content, int type, String remark);

	void modifyTemplate(String code, String name, String content, int type, String remark);

	void deleteTemplate(String code);

	void getTemplate(String templateId);

	void listTemplate(int pageIndex, int pageSize);

}
