package com.sugarweb.digitalHuman.component.llm.thought;

import lombok.Data;

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
public class ThoughtRequest {

    /**
     * 每一次的思考id
     */
    private long thoughtId;

    /**
     * 当前提问消息 不包含system msg
     */
    private List<RoleMsg> roleMsgList;

    /**
     * 系统消息
     */
    private String systemMsg;

    /**
     * 上下文变量
     */
    private final Map<String, Object> contextVariables = new HashMap<>();

    public void put(String key, Object value) {
        contextVariables.put(key, value);
    }

}
