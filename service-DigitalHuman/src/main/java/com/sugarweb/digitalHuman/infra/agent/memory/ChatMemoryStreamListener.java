package com.sugarweb.digitalHuman.infra.agent.memory;

import com.sugarweb.digitalHuman.infra.agent.think.StreamListener;
import com.sugarweb.digitalHuman.infra.agent.think.ThinkContext;
import com.sugarweb.digitalHuman.domain.ChatMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * StreamTokenSpeakAdapt
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class ChatMemoryStreamListener implements StreamListener {

    private final ChatMemoryComponent chatMemoryComponent;

    public ChatMemoryStreamListener(ChatMemoryComponent chatMemoryComponent) {
        this.chatMemoryComponent = chatMemoryComponent;
    }

    @Override
    public void onNext(ThinkContext thinkContext, String token) {

    }

    @Override
    public void onComplete(ThinkContext thinkContext) {
        //保存对话消息
        ChatMsg currentQuestion = thinkContext.getQuestionMsg();
        ChatMsg aiMessage = thinkContext.getAssistantMsg();
        List<ChatMsg> chatMsgList = new ArrayList<>();
        chatMsgList.add(currentQuestion);
        chatMsgList.add(aiMessage);
        chatMemoryComponent.saveBatchChatMsg(chatMsgList);
    }

    @Override
    public void onError(ThinkContext thinkContext, Throwable error) {

    }
}
