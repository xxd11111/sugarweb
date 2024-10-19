package com.sugarweb.chatAssistant.agent.ability.memory;

import com.sugarweb.chatAssistant.agent.ability.think.StreamListener;
import com.sugarweb.chatAssistant.agent.ability.think.ThinkContext;
import com.sugarweb.chatAssistant.domain.ChatMsg;
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

    private final MemoryAbility memoryAbility;

    public MemoryOutputListener(MemoryAbility memoryAbility) {
        this.memoryAbility = memoryAbility;
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
        memoryAbility.saveBatchChatMsg(chatMsgList);
    }

    @Override
    public void onError(ThinkContext thinkContext, Throwable error) {

    }
}
