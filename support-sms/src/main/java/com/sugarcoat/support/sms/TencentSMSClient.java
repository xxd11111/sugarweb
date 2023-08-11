package com.sugarcoat.support.sms;

import com.sugarcoat.api.sms.SMSClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;

/**
 * 腾讯sms客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/8
 */
public class TencentSMSClient implements SMSClient {

	private String accessKeyId;

	private String accessKeySecret;

	private String endpoint;

	private String signName;

	private SmsClient client;

	public TencentSMSClient() {
		Credential credential = new Credential(accessKeyId, accessKeySecret);
		HttpProfile httpProfile = new HttpProfile();
		httpProfile.setEndpoint(endpoint);
		ClientProfile clientProfile = new ClientProfile();
		clientProfile.setHttpProfile(httpProfile);
		this.client = new SmsClient(credential, "", clientProfile);
	}

	@Override
	public void sendMessage(String templateId, String phoneNumber, String[] params) {
		SendSmsRequest request = new SendSmsRequest();
		request.setTemplateID(templateId);
		request.setPhoneNumberSet(new String[] { phoneNumber });
		request.setTemplateParamSet(params);
		try {
			SendSmsResponse response = client.SendSms(request);
		}
		catch (TencentCloudSDKException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendMessage(String templateId, String[] phoneNumbers, String[] params) {
		SendSmsRequest request = new SendSmsRequest();
		request.setTemplateID(templateId);
		request.setPhoneNumberSet(phoneNumbers);
		request.setTemplateParamSet(params);
		try {
			SendSmsResponse response = client.SendSms(request);
		}
		catch (TencentCloudSDKException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addTemplate(String name, String content, int type, String remark) {
		AddSmsTemplateRequest request = new AddSmsTemplateRequest();
		request.setInternational(System.currentTimeMillis());
		request.setTemplateName(name);
		request.setTemplateContent(content);
		request.setSmsType((long) type);
		request.setRemark(remark);
		try {
			AddSmsTemplateResponse response = client.AddSmsTemplate(request);
		}
		catch (TencentCloudSDKException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifyTemplate(String id, String name, String content, int type, String remark) {
		ModifySmsTemplateRequest request = new ModifySmsTemplateRequest();
		request.setInternational(Long.valueOf(id));
		request.setTemplateName(name);
		request.setTemplateContent(content);
		request.setSmsType((long) type);
		request.setRemark(remark);
		try {
			ModifySmsTemplateResponse response = client.ModifySmsTemplate(request);
		}
		catch (TencentCloudSDKException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTemplate(String id) {
		DeleteSmsTemplateRequest request = new DeleteSmsTemplateRequest();
		request.setTemplateId(Long.valueOf(id));
		try {
			DeleteSmsTemplateResponse response = client.DeleteSmsTemplate(request);
		}
		catch (TencentCloudSDKException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getTemplate(String templateId) {
		DescribeSmsTemplateListRequest request = new DescribeSmsTemplateListRequest();
		request.setInternational(Long.valueOf(templateId));
		DescribeSmsTemplateListResponse response = null;
		try {
			response = client.DescribeSmsTemplateList(request);
		}
		catch (TencentCloudSDKException e) {
			e.printStackTrace();
		}
		DescribeTemplateListStatus[] describeTemplateStatusSet = response.getDescribeTemplateStatusSet();
		for (DescribeTemplateListStatus describeTemplateListStatus : describeTemplateStatusSet) {

		}
	}

	@Override
	public void listTemplate(int pageIndex, int pageSize) {

	}

}
