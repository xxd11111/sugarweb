package com.sugarweb.digitalHuman.domain;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
import com.sugarweb.digitalHuman.constants.ChatRole;
import com.sugarweb.digitalHuman.infra.llm.thought.RoleMsg;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * PerformanceConversation
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StagePerformanceMsg extends BaseEntity {

    @TableId
    private String msgId;

    private String msgPid;

    private String performanceId;

    private String stageId;

    private String chatModelId;

    private String actorId;

    private String systemMsg;

    private String question;

    private String answer;

    private String message;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**
     * 单位：毫秒
     */
    private Long costTime;

    /**
     * 0:用户消息，1:系统消息  user script
     */
    private String msgType;

    private String userId;

    public List<RoleMsg> prepareHistoryMessage() {
        List<RoleMsg> roleMsgList = JSONUtil.toList(getMessage(), RoleMsg.class);
        roleMsgList.add(new RoleMsg(ChatRole.ASSISTANT.getValue(), getAnswer()));
        return roleMsgList;
    }
}
