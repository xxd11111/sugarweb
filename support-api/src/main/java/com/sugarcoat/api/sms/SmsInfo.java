package com.sugarcoat.api.sms;

import java.util.Collection;

/**
 * 短信信息
 *
 * @author 许向东
 * @date 2023/9/15
 */
public interface SmsInfo {

    String getTemplateId();

    Collection<String> getPhoneNumbers();

    Collection<String> getParams();


}
