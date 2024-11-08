package com.sugarweb.digitalHuman.infra.agent.think;

import com.sugarweb.digitalHuman.domain.BlblUser;
import com.sugarweb.digitalHuman.domain.ChatMsg;
import com.sugarweb.digitalHuman.domain.PerformanceMsg;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 思考内容上下文
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ThinkContext {

    /**
     * ai每一次的思考id
     */
    private long thinkId;

    /**
     * 表演id
     */
    private String performanceId;

    /**
     * 历史消息
     */
    private List<ChatMsg> historyMsgList;

    /**
     * 当前提问消息
     */
    private ChatMsg questionMsg;

    /**
     * 系统消息
     */
    private ChatMsg systemMsg;

    /**
     * 本次ai响应消息
     */
    private ChatMsg assistantMsg;

    /**
     * 上下文变量
     */
    private final Map<String, Object> contextVariables = new HashMap<>();

    /**
     * 本次思考的开始时间
     */
    private LocalDateTime startTime;

    /**
     * 本次思考的结束时间
     */
    private LocalDateTime endTime;

    /**
     * 本次表演的消息 todo 关于chatmsg与performanceMsg 存在设计争议
     */
    private PerformanceMsg performanceMsg;

    public void put(String key, Object value) {
        contextVariables.put(key, value);
    }

    public String getBlblUid () {
        BlblUser user = (BlblUser) contextVariables.get("user");
        return user.getBlblUid();
    }

}
