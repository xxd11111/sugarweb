package com.sugarcoat.support.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.sugarcoat.api.exception.FrameworkException;
import com.sugarcoat.api.exception.ValidateException;
import com.sugarcoat.api.sms.SmsClient;
import com.sugarcoat.api.sms.SmsInfo;
import com.sugarcoat.api.sms.SmsTemplateInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * 阿里云sms客户端  todo 暂停，等开通平台做调试
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/8
 */
@Slf4j
public class AliyunSmsClient implements SmsClient {

    private final Client client;

    private final String signName;

    public AliyunSmsClient(AliyunSmsProperties aliyunSmsProperties) {
        try {
            this.signName = aliyunSmsProperties.getSignName();
            String accessKeyId = aliyunSmsProperties.getAccessKeyId();
            String accessKeySecret = aliyunSmsProperties.getAccessKeySecret();
            String endpoint = aliyunSmsProperties.getEndpoint();
            Config config = new Config()
                    .setAccessKeyId(accessKeyId)
                    .setAccessKeySecret(accessKeySecret)
                    .setEndpoint(endpoint);
            this.client = new Client(config);
        } catch (Exception e) {
            throw new FrameworkException("阿里云短信初始化异常", e);
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

    public void sendMessage(String templateId, String phoneNumber, String params) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new ValidateException("目标不能为空");
        }
        SendSmsRequest req = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(signName)
                .setTemplateCode(templateId)
                .setTemplateParam(params);
        try {
            SendSmsResponse resp = client.sendSms(req);
            SendSmsResponseBody body = resp.getBody();
            Integer statusCode = resp.getStatusCode();
            String code = body.getCode();
            String message = body.getMessage();
            String bizId = body.getBizId();
            String requestId = body.getRequestId();
            log.info("短信发送结果,statusCode:{},code:{}, message:{}, bizId:{}, requestId:{}", statusCode, code, message, bizId, requestId);
        } catch (Exception e) {
            throw new SmsException(e.getMessage(), e);
        }
    }

    public void sendMessage(String templateId, String[] phoneNumbers, String[] params) {
        if (phoneNumbers == null || phoneNumbers.length == 0) {
            throw new ValidateException("目标不能为空");
        }
        if (params == null || params.length == 0) {
            throw new ValidateException("参数不能为空");
        }
        // todo params
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        request.setPhoneNumberJson(null)
                .setSignNameJson(signName)
                .setTemplateCode(templateId)
                .setTemplateParamJson(null);
        try {
            SendBatchSmsResponse response = client.sendBatchSms(request);
        } catch (Exception e) {
            throw new FrameworkException(e.getMessage());
        }
    }

    public void addTemplate(String name, String content, int type, String remark) {
        AddSmsTemplateRequest request = new AddSmsTemplateRequest();
        request.setTemplateName(name);
        request.setTemplateContent(content);
        request.setTemplateType(type);
        request.setRemark(remark);
        AddSmsTemplateResponse response = null;
        try {
            response = client.addSmsTemplate(request);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        AddSmsTemplateResponseBody body = response.getBody();
        String templateCode = body.getTemplateCode();
    }

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
        } catch (Exception e) {
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
        } catch (Exception e) {
            // e.printStackTrace();
        }
        DeleteSmsTemplateResponseBody body = response.getBody();
    }

    @Override
    public SmsTemplateInfo getTemplate(String templateId) {
        // 构建参数
        QuerySmsTemplateRequest request = new QuerySmsTemplateRequest();
        request.setTemplateCode(templateId);
        try {
            // 执行请求
            QuerySmsTemplateResponse response = client.querySmsTemplate(request);
            QuerySmsTemplateResponseBody body = response.getBody();
            String templateCode = body.getTemplateCode();
            String templateContent = body.getTemplateContent();
            String templateName = body.getTemplateName();
            String createDate = body.getCreateDate();
            String reason = body.getReason();
            Integer templateStatus = body.getTemplateStatus();
            Integer templateType = body.getTemplateType();
            ScSmsTemplateInfo scSmsTemplateInfo = new ScSmsTemplateInfo();
            return scSmsTemplateInfo;
        } catch (Exception e) {
            throw new SmsException("短信请求失败", e);
        }

    }

    @Override
    public Collection<SmsTemplateInfo> listTemplate(int pageIndex, int pageSize) {
        QuerySmsTemplateListRequest request = new QuerySmsTemplateListRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        QuerySmsTemplateListResponse response = null;
        try {
            response = client.querySmsTemplateList(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (QuerySmsTemplateListResponseBody.QuerySmsTemplateListResponseBodySmsTemplateList querySmsTemplateListResponseBodySmsTemplateList : response
                .getBody().getSmsTemplateList()) {
        }
        return null;
    }

    private void checkResponseStatus(Integer status) throws SmsException {
        if (status != 200) {
            throw new SmsException("");
        }
    }

}
