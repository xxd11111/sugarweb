package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class TemplateVariableInfo {
    @TableId
    private String variableId;
    private String templateId;
    private String variableCode;
    private String variableName;
}
