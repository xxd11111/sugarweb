package com.sugarweb.digitalHuman.infra.llm.thought;

import cn.hutool.json.JSONUtil;
import com.sugarweb.digitalHuman.constants.ChatRole;
import com.sugarweb.digitalHuman.domain.StagePerformanceMsg;
import com.sugarweb.digitalHuman.infra.llm.ModelFactory;
import com.sugarweb.digitalHuman.infra.llm.StageContext;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 思考能力是协调各个能力的中心
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class StreamThoughtComponent {

    private final StreamingChatLanguageModel chatLanguageModel;

    private final List<StreamThoughtListener> listeners;

    @Builder
    public StreamThoughtComponent(StageContext stageContext, List<StreamThoughtListener> listeners) {
        this.chatLanguageModel = ModelFactory.creatStreamingChatLanguageModel(stageContext.getActor().getChatModelId());
        this.listeners = listeners;
    }

    public void streamThink(ThoughtContext thoughtContext) {
        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        String systemChatMsg = thoughtContext.getSystemMsg();
        if (systemChatMsg != null) {
            ChatMessage systemMessage = new SystemMessage(systemChatMsg);
            messageList.add(systemMessage);
        }

        //第二步，获取ai对话历史消息
        StagePerformanceMsg historyMessage = thoughtContext.getHistoryMsg();
        List<ChatMessage> hisMsg = buildHisMsg(historyMessage);
        messageList.addAll(hisMsg);


        //第三步，获取当前提问的消息
        String question = thoughtContext.getQuestionMsg();
        messageList.add(new UserMessage(question));


        chatLanguageModel.generate(messageList, new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
                for (StreamThoughtListener listener : listeners) {
                    listener.onNext(thoughtContext, token);
                }
            }

            @Override
            public void onError(Throwable error) {
                for (StreamThoughtListener listener : listeners) {
                    listener.onError(thoughtContext, error);
                }
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                StreamingResponseHandler.super.onComplete(response);
                AiMessage aiMessage = response.content();
                thoughtContext.setAssistantMsg(aiMessage.text());
                for (StreamThoughtListener listener : listeners) {
                    listener.onComplete(thoughtContext);
                }
            }
        });
    }

    private List<ChatMessage> buildHisMsg(StagePerformanceMsg lastHistoryMsg) {
        String msg = lastHistoryMsg.getHistoryMsg();
        List<RoleMsg> roleMsgList = JSONUtil.toList(msg, RoleMsg.class);
        roleMsgList.add(new RoleMsg(ChatRole.ASSISTANT.getValue(), lastHistoryMsg.getAnswer()));

        List<ChatMessage> chatMessages = new ArrayList<>();
        for (RoleMsg roleMsg : roleMsgList) {
            if (ChatRole.USER.getValue().equals(roleMsg.getRole())) {
                chatMessages.add(new UserMessage(roleMsg.getContent()));
            } else if (ChatRole.ASSISTANT.getValue().equals(roleMsg.getRole())) {
                chatMessages.add(new AiMessage(roleMsg.getContent()));
            }
        }
        chatMessages.add(new AiMessage(lastHistoryMsg.getAnswer()));

        return chatMessages;
    }

    @Data
    @AllArgsConstructor
    public static class RoleMsg {

        private String role;

        private String content;

    }

}
