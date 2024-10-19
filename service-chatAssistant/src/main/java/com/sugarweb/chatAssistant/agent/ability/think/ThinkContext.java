package com.sugarweb.chatAssistant.agent.ability.think;

import com.sugarweb.chatAssistant.domain.BlblUser;
import com.sugarweb.chatAssistant.domain.ChatMsg;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 思考内容封装
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ThinkContext {

    private long thinkId;

    private String memoryId;

    private List<ChatMsg> historyMsgList;

    private ChatMsg questionMsg;

    private ChatMsg systemMsg;

    private ChatMsg assistantMsg;

    private final Map<String, Object> contextVariables = new HashMap<>();

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public void put(String key, Object value) {
        contextVariables.put(key, value);
    }

    public String getBlblUid () {
        BlblUser user = (BlblUser) contextVariables.get("user");
        return user.getBlblUid();
    }

}
