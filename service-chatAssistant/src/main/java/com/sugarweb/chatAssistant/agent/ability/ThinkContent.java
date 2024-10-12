package com.sugarweb.chatAssistant.agent.ability;

import com.sugarweb.chatAssistant.domain.po.MessageInfo;
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

    private List<MessageInfo> historyMessage;

    private MessageInfo currentQuestion;

    private MessageInfo systemMessage;

}
