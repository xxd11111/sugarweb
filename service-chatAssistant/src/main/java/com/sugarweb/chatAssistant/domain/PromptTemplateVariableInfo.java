package com.sugarweb.chatAssistant.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 提示词变量信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PromptTemplateVariableInfo {
    @TableId
    private String variableId;

    private String templateId;

    private String variableCode;

    private String variableName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
