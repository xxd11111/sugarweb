package com.sugarcoat.api.sms;

/**
 * 短信模板信息
 *
 * @author 许向东
 * @date 2023/9/15
 */
public interface SmsTemplateInfo {

    String getTemplateId();

    String getContent();

    int getType();

    String getRemark();

}
