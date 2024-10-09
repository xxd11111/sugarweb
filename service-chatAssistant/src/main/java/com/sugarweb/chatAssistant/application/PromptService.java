package com.sugarweb.chatAssistant.application;

import com.sugarweb.chatAssistant.domain.po.PromptTemplateInfo;
import com.sugarweb.chatAssistant.domain.po.TemplateVariableInfo;

import java.util.List;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
public class PromptService {

    public PromptTemplateInfo getSystemPrompt(String agentId){
        //todo 目前写死
        PromptTemplateInfo templateInfo = new PromptTemplateInfo();
        String text = """
                你现在是友好的人类【炫妹】，接下来请根据事件消息做出回答；
                要求：你的每句回答都会以语音的形式直接展现给观众，结果以口语化的形式表达，不能用书面语言，返回结果只能是中文。
                                
                以下是参考文档：
                {{documents}}
                """;
        templateInfo.setTemplateContent(text);

        TemplateVariableInfo variableInfo = new TemplateVariableInfo();
        variableInfo.setVariableCode("documents");
        templateInfo.setTemplateVariableList(List.of(variableInfo));
        return templateInfo;
    }

    public PromptTemplateInfo getUserPrompt(String agentId){
        //todo 目前写死
        PromptTemplateInfo templateInfo = new PromptTemplateInfo();
        String text = """
                {{question}}
                """;
        templateInfo.setTemplateContent(text);

        TemplateVariableInfo variableInfo = new TemplateVariableInfo();
        variableInfo.setVariableCode("question");
        templateInfo.setTemplateVariableList(List.of(variableInfo));
        return templateInfo;
    }



























}
