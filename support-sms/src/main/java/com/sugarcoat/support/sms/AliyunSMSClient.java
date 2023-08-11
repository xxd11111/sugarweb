package com.sugarcoat.support.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.sugarcoat.api.sms.SMSClient;
import com.sugarcoat.support.sms.exception.SmsException;

/**
 * todo 阿里云sms客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/8
 */
public class AliyunSMSClient implements SMSClient {

	private Client client;

	private String accessKeyId;

	private String accessKeySecret;

	private String endpoint;

	private String signName;

	public AliyunSMSClient(Client client) throws Exception {
		Config config = new Config()
				// 您的AccessKey ID
				.setAccessKeyId(accessKeyId)
				// 您的AccessKey Secret
				.setAccessKeySecret(accessKeySecret)
				// 访问的域名
				.setEndpoint(endpoint);
		this.client = new Client(config);
	}

	@Override
	public void sendMessage(String templateId, String phoneNumber, String[] params) {
		if (phoneNumber == null || phoneNumber.isEmpty()) {
			throw new SmsException("目标不能为空");
		}
		if (params == null || params.length == 0) {
			throw new SmsException("参数不能为空");
		}
		// todo params
		SendSmsRequest req = new SendSmsRequest().setPhoneNumbers(phoneNumber).setSignName(signName)
				.setTemplateCode(templateId).setTemplateParam(null);
		try {
			SendSmsResponse resp = client.sendSms(req);
		}
		catch (Exception e) {
			throw new SmsException(e.getMessage());
		}
	}

	@Override
	public void sendMessage(String templateId, String[] phoneNumbers, String[] params) {
		if (phoneNumbers == null || phoneNumbers.length == 0) {
			throw new SmsException("目标不能为空");
		}
		if (params == null || params.length == 0) {
			throw new SmsException("参数不能为空");
		}
		// todo params
		SendBatchSmsRequest request = new SendBatchSmsRequest();
		request.setPhoneNumberJson(null).setSignNameJson(signName).setTemplateCode(templateId)
				.setTemplateParamJson(null);
		try {
			SendBatchSmsResponse response = client.sendBatchSms(request);
		}
		catch (Exception e) {
			throw new SmsException(e.getMessage());
		}
	}

	@Override
	public void addTemplate(String name, String content, int type, String remark) {
		AddSmsTemplateRequest request = new AddSmsTemplateRequest();
		request.setTemplateName(name);
		request.setTemplateContent(content);
		request.setTemplateType(type);
		request.setRemark(remark);
		AddSmsTemplateResponse response = null;
		try {
			response = client.addSmsTemplate(request);
		}
		catch (Exception e) {
			// e.printStackTrace();
		}
		AddSmsTemplateResponseBody body = response.getBody();
		String templateCode = body.getTemplateCode();
	}

	@Override
	public void modifyTemplate(String code, String name, String content, int type, String remark) {
		ModifySmsTemplateRequest request = new ModifySmsTemplateRequest();
		request.setTemplateCode(code);
		request.setTemplateName(name);
		request.setTemplateContent(content);
		request.setTemplateType(type);
		request.setRemark(remark);
		ModifySmsTemplateResponse response = null;
		try {
			response = client.modifySmsTemplate(request);
		}
		catch (Exception e) {
			// e.printStackTrace();
		}
		ModifySmsTemplateResponseBody body = response.getBody();
	}

	@Override
	public void deleteTemplate(String code) {
		DeleteSmsTemplateRequest request = new DeleteSmsTemplateRequest();
		request.setTemplateCode(code);
		DeleteSmsTemplateResponse response = null;
		try {
			response = client.deleteSmsTemplate(request);
		}
		catch (Exception e) {
			// e.printStackTrace();
		}
		DeleteSmsTemplateResponseBody body = response.getBody();
	}

	@Override
	public void getTemplate(String templateId) {
		// 构建参数
		QuerySmsTemplateRequest request = new QuerySmsTemplateRequest();
		request.setTemplateCode(templateId);
		// 执行请求
		QuerySmsTemplateResponse response = null;
		try {
			response = client.querySmsTemplate(request);
		}
		catch (Exception e) {
			// e.printStackTrace();
		}
		QuerySmsTemplateResponseBody body = response.getBody();
	}

	@Override
	public void listTemplate(int pageIndex, int pageSize) {
		QuerySmsTemplateListRequest request = new QuerySmsTemplateListRequest();
		request.setPageIndex(pageIndex);
		request.setPageSize(pageSize);
		QuerySmsTemplateListResponse response = null;
		try {
			response = client.querySmsTemplateList(request);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		for (QuerySmsTemplateListResponseBody.QuerySmsTemplateListResponseBodySmsTemplateList querySmsTemplateListResponseBodySmsTemplateList : response
				.getBody().getSmsTemplateList()) {

		}
	}

}
