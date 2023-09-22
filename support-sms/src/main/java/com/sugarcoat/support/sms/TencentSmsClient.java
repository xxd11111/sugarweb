package com.sugarcoat.support.sms;

import com.sugarcoat.protocol.sms.SmsClient;
import com.sugarcoat.protocol.sms.SmsInfo;
import com.sugarcoat.protocol.sms.SmsTemplateInfo;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.models.*;

import java.util.Collection;

/**
 * 腾讯sms客户端 todo 暂停，等开通平台做调试
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/8
 */
public class TencentSmsClient implements SmsClient {

	private String signName;

	private final com.tencentcloudapi.sms.v20190711.SmsClient client;

	public TencentSmsClient(TencentSmsProperties tencentSmsProperties) {
		String accessKeyId = tencentSmsProperties.getAccessKeyId();
		String accessKeySecret = tencentSmsProperties.getAccessKeySecret();
		String endpoint = tencentSmsProperties.getEndpoint();
		this.signName = tencentSmsProperties.getSignName();
		Credential credential = new Credential(accessKeyId, accessKeySecret);
		HttpProfile httpProfile = new HttpProfile();
		httpProfile.setEndpoint(endpoint);
		ClientProfile clientProfile = new ClientProfile();
		clientProfile.setHttpProfile(httpProfile);
		this.client = new com.tencentcloudapi.sms.v20190711.SmsClient(credential, "", clientProfile);
	}

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
	public void sendMessage(SmsInfo smsInfo) {

	}

	@Override
	public void addTemplate(SmsTemplateInfo smsTemplateInfo) {

	}

	@Override
	public void modifyTemplate(SmsTemplateInfo smsTemplateInfo) {

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
	public SmsTemplateInfo getTemplate(String templateId) {
		DescribeSmsTemplateListRequest request = new DescribeSmsTemplateListRequest();
		request.setInternational(Long.valueOf(templateId));
		request.setTemplateIdSet(new Long[]{Long.valueOf(templateId)});
		DescribeSmsTemplateListResponse response = null;
		try {
			response = client.DescribeSmsTemplateList(request);
		}
		catch (TencentCloudSDKException e) {
			e.printStackTrace();
		}
		DescribeTemplateListStatus[] describeTemplateStatusSet = response.getDescribeTemplateStatusSet();
		for (DescribeTemplateListStatus describeTemplateListStatus : describeTemplateStatusSet) {
			ScSmsTemplateInfo scSmsTemplateInfo = new ScSmsTemplateInfo();
		}
		return null;
	}

	@Override
	public Collection<SmsTemplateInfo> listTemplate(int pageIndex, int pageSize) {
		DescribeSmsTemplateListRequest req = new DescribeSmsTemplateListRequest();
		try {
			DescribeSmsTemplateListResponse describeSmsTemplateListResponse = client.DescribeSmsTemplateList(req);
		} catch (TencentCloudSDKException e) {
			e.printStackTrace();
		}
		return null;
	}

}
