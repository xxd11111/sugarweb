package com.sugarweb.chatAssistant.agent.ability;

import com.sugarweb.chatAssistant.domain.po.ChatMsg;
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

    private List<ChatMsg> historyMessage;

    private ChatMsg currentQuestion;

    private ChatMsg systemMessage;

}
