package com.sugarweb.digitalHuman.application;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.domain.PromptTemplateInfo;
import com.sugarweb.digitalHuman.domain.PromptTemplateVariableInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PromptService
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class PromptService {

    public PromptTemplateInfo defaultSystemPrompt() {
        PromptTemplateInfo templateInfo = new PromptTemplateInfo();
        String text = """
                你现在是友好的人类【炫妹】，接下来请根据事件消息做出回答；
                要求：你的每句回答都会以语音的形式直接展现给观众，结果以口语化的形式表达，不能用书面语言，返回结果只能是中文。
                
                以下是参考文档：
                {{documents}}
                """;
        templateInfo.setContent(text);
        templateInfo.setVariables(new String[]{"documents"});
        return templateInfo;
    }

    public PromptTemplateInfo getById(String systemPromptId) {
        return Db.getById(systemPromptId, PromptTemplateInfo.class);
    }
}
