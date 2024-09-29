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
                你现在是个直播机器人【炫东】，请根据事件消息做出互动,要求活泼，搞怪。
                                
                以下是参考文档：
                {{documents}}
                """;
        return text;
    }

}
