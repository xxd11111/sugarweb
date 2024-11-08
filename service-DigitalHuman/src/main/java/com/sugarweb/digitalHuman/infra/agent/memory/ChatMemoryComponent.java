package com.sugarweb.digitalHuman.infra.agent.memory;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.constants.ChatRole;
import com.sugarweb.digitalHuman.domain.ChatMsg;

import java.util.Collections;
import java.util.List;

/**
 * ChatMemoryComponent
 * 聊天记忆组件
 *
 * @author xxd
 * @version 1.0
 */
public class ChatMemoryComponent {

    private final String performanceId;

    public ChatMemoryComponent(String performanceId) {
        this.performanceId = performanceId;
    }

    public List<ChatMsg> lastChatMessage(int limit) {
        List<ChatMsg> chatMemoryInfos = Db.lambdaQuery(ChatMsg.class)
                .eq(ChatMsg::getPerformanceId, performanceId)
                .orderByDesc(ChatMsg::getCreateTime)
                .in(ChatMsg::getChatRole, ChatRole.USER.getValue(), ChatRole.ASSISTANT.getValue())
                .last(limit > 0, "limit " + limit)
                .list();
        Collections.reverse(chatMemoryInfos);
        return chatMemoryInfos;
    }

    public List<ChatMsg> lastChatMessage(String userId, int limit) {
        List<ChatMsg> chatMemoryInfos = Db.lambdaQuery(ChatMsg.class)
                .eq(ChatMsg::getPerformanceId, performanceId)
                .eq(ChatMsg::getUserId, userId)
                .orderByDesc(ChatMsg::getCreateTime)
                .in(ChatMsg::getChatRole, ChatRole.USER.getValue(), ChatRole.ASSISTANT.getValue())
                .last(limit > 0, "limit " + limit)
                .list();
        Collections.reverse(chatMemoryInfos);
        return chatMemoryInfos;
    }

    public void saveBatchChatMsg(List<ChatMsg> chatMemoryInfo) {
        Db.saveBatch(chatMemoryInfo);
    }

    public void saveChatMessage(ChatMsg chatMemoryInfo) {
        Db.save(chatMemoryInfo);
    }

}
