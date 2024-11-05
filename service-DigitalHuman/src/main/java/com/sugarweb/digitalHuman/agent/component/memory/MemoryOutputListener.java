package com.sugarweb.digitalHuman.agent.component.memory;

import com.sugarweb.digitalHuman.agent.component.think.StreamListener;
import com.sugarweb.digitalHuman.agent.component.think.ThinkContext;
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
public class MemoryOutputListener implements StreamListener {

    private final MemoryComponent memoryComponent;

    public MemoryOutputListener(MemoryComponent memoryComponent) {
        this.memoryComponent = memoryComponent;
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
        memoryComponent.saveBatchChatMsg(chatMsgList);
    }

    @Override
    public void onError(ThinkContext thinkContext, Throwable error) {

    }
}
