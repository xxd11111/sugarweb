package com.sugarcoat.support.sms;

import com.sugarcoat.api.sms.SmsTemplateInfo;
import lombok.Data;

/**
 * sugarcoat短信模板信息
 *
 * @author 许向东
 * @date 2023/9/15
 */
@Data
public class ScSmsTemplateInfo implements SmsTemplateInfo {

    private String templateId;

    private String content;

    private int type;

    private String remark;

}
