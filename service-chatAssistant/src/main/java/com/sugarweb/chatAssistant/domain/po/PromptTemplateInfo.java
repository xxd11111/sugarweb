package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * PromptTemplateInfo
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PromptTemplateInfo {
    @TableId
    private String templateId;
    private String agentId;
    private String templateType;
    private String templateName;
    private String templateContent;
    private String createTime;
    private String updateTime;
    @TableField(exist = false)
    private List<TemplateVariableInfo> templateVariableList;
}
