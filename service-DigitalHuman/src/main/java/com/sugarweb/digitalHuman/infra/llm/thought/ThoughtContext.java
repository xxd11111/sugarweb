package com.sugarweb.digitalHuman.infra.llm.thought;

import com.sugarweb.digitalHuman.domain.BlblUser;
import com.sugarweb.digitalHuman.domain.StagePerformanceMsg;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 思考内容上下文
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ThoughtContext {

    /**
     * 每一次的思考id
     */
    private long thoughtId;

    /**
     * 本次表演的消息
     */
    private StagePerformanceMsg currentMsg;

    /**
     * 历史消息
     */
    private StagePerformanceMsg historyMsg;

    /**
     * 当前提问消息
     */
    private String questionMsg;

    /**
     * 系统消息
     */
    private String systemMsg;

    /**
     * 本次ai响应消息
     */
    private String assistantMsg;

    /**
     * 上下文变量
     */
    private final Map<String, Object> contextVariables = new HashMap<>();

    public void put(String key, Object value) {
        contextVariables.put(key, value);
    }

    public String getBlblUid() {
        BlblUser user = (BlblUser) contextVariables.get("user");
        return user.getBlblUid();
    }

}
