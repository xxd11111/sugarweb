package com.sugarweb.chatAssistant.domain;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 提示词模板信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PromptTemplateInfo {
    @TableId
    private String promptId;

    private String agentId;

    /**
     * system, user
     */
    private String promptType;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<PromptTemplateVariableInfo> promptVariableList;

    public String getPrompt(Map<String, Object> contextVariables) {
        String content = this.content;
        for (PromptTemplateVariableInfo variableInfo : promptVariableList) {
            String variableCode = "{{" + variableInfo.getVariableCode() + "}}";
            Object variableValue = contextVariables.get(variableCode);
            content = StrUtil.replace(content, variableCode, variableValue == null ? "" : variableValue.toString());
        }
        return content;
    }
}
