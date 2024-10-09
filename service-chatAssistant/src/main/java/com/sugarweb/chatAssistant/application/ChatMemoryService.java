package com.sugarweb.chatAssistant.application;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.po.ChatMemoryInfo;
import com.sugarweb.chatAssistant.domain.po.ChatMessageInfo;

import java.util.Collections;
import java.util.List;

/**
 * TODO
 *
 * @author xxd
 * @since 2024/9/28 10:02
 */
public class ChatMemoryService {

    // 瞬时记忆场景
    // 1.单用户对话场景
    // 2.多用户对话场景
    // 长时记忆
    // 1.构建对用户的聊天记忆
    // 2.构建对用户的印象、行为、状态的记忆
    // 3.技术记忆，构建学习能力
    // 隐私机制
    // 1.用户隐私数据不公开

    public List<ChatMessageInfo> listChatMessage(String memoryId, int limit) {
        List<ChatMessageInfo> chatMemoryInfos = Db.lambdaQuery(ChatMessageInfo.class)
                .eq(ChatMessageInfo::getMemoryId, memoryId)
                .orderByDesc(ChatMessageInfo::getCreateTime)
                .last(limit > 0, "limit " + limit)
                .list();
        Collections.reverse(chatMemoryInfos);
        return chatMemoryInfos;
    }


}
