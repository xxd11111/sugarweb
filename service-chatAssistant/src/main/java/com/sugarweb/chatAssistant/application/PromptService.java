package com.sugarweb.chatAssistant.application;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
public class PromptService {

    /**
     * 获取提示词
     */
    public String getPrompt() {
        String text = """
                {{question}}
                """;
        return text;
    }

    /**
     * 获取系统提示词
     */
    public String getSystemPrompt() {
        String text = """
                你现在是友好的人类【炫妹】，接下来请根据事件消息做出回答；
                要求：你的每句回答都会以语音的形式直接展现给观众，结果以口语化的形式表达，不能用书面语言，返回结果只能是中文。
                                
                以下是参考文档：
                {{documents}}
                """;
        return text;
    }

}
