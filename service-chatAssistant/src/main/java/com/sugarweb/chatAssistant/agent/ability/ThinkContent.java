package com.sugarweb.chatAssistant.agent.ability;

import com.sugarweb.chatAssistant.domain.po.ChatMessageInfo;
import lombok.Data;

import java.util.List;

/**
 * 思考内容封装
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ThinkContent {

    private List<ChatMessageInfo> historyMessage;

    private ChatMessageInfo currentQuestion;

    private ChatMessageInfo systemMessage;

}
