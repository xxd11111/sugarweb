package com.sugarweb.chatAssistant.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.po.ChatMemoryInfo;
import com.sugarweb.chatAssistant.domain.po.ChatMessageInfo;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

import java.util.List;

/**
 * TODO
 *
 * @author 许向东
 * @version 1.0
 */
public class MyChatMemory implements ChatMemoryStore {

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        List<ChatMessageInfo> list = Db.list(new LambdaQueryWrapper<>(ChatMessageInfo.class).eq(ChatMessageInfo::getMemoryId, memoryId));
        return null;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {

    }

    @Override
    public void deleteMessages(Object memoryId) {

    }

}
