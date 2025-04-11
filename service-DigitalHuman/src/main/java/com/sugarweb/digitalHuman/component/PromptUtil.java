package com.sugarweb.digitalHuman.component;

import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * PromptUtil
 *
 * @author xxd
 * @since 2024/11/16 23:01
 */
public class PromptUtil {

    public static String getPrompt(String promptTemplate, Map<String, Object> contextVariables) {
        String[] variables = parsePromptVariables(promptTemplate);
        for (String variableCode : variables) {
            String variableReplace = "{{" + variableCode + "}}";
            Object variableValue = contextVariables.get(variableCode);
            promptTemplate = StrUtil.replace(promptTemplate, variableReplace, variableValue == null ? "" : variableValue.toString());
        }
        return promptTemplate;
    }

    /**
     * 获得模板变量，模板变量格式为 {{变量名}}
     */
    public static String[] parsePromptVariables(String promptTemplate) {
        return StrUtil.subBetweenAll(promptTemplate, "{{", "}}");
    }

}
