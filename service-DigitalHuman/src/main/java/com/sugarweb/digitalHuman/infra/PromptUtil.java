package com.sugarweb.digitalHuman.infra;

import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * PromptUtil
 *
 * @author xxd
 * @since 2024/11/16 23:01
 */
public class PromptUtil {

    public static String getPrompt(String promptTemplate, String[] variables, Map<String, Object> contextVariables) {
        for (String variableCode : variables) {
            String variableReplace = "{{" + variableCode + "}}";
            Object variableValue = contextVariables.get(variableCode);
            promptTemplate = StrUtil.replace(promptTemplate, variableReplace, variableValue == null ? "" : variableValue.toString());
        }
        return promptTemplate;
    }

}
