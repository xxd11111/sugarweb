package com.sugarweb.digitalHuman.infra.llm.thought;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.digitalHuman.constants.ChatRole;
import com.sugarweb.digitalHuman.infra.llm.ModelFactory;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 思考能力
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class StreamThoughtComponent {

    private final StreamingChatLanguageModel chatLanguageModel;

    private final List<StreamThoughtListener> listeners;

    @Builder
    public StreamThoughtComponent(String modelId, List<StreamThoughtListener> listeners) {
        this.chatLanguageModel = ModelFactory.creatStreamingChatLanguageModel(modelId);
        this.listeners = listeners;
    }

    public Future<String> streamThink(ThoughtRequest thoughtRequest) {
        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        String systemChatMsg = thoughtRequest.getSystemMsg();
        if (StrUtil.isNotEmpty(systemChatMsg)) {
            ChatMessage systemMessage = new SystemMessage(systemChatMsg);
            messageList.add(systemMessage);
        }

        //第二步，获取ai对话历史消息
        List<RoleMsg> roleMsgList = thoughtRequest.getRoleMsgList();
        if (CollUtil.isNotEmpty(roleMsgList)) {
            for (RoleMsg roleMsg : roleMsgList) {
                if (ChatRole.USER.getValue().equals(roleMsg.getRole())) {
                    if (StrUtil.isNotEmpty(roleMsg.getContent())){
                        messageList.add(UserMessage.from(roleMsg.getContent()));
                    }
                } else if (ChatRole.ASSISTANT.getValue().equals(roleMsg.getRole())) {
                    if (StrUtil.isNotEmpty(roleMsg.getContent())){
                        messageList.add(AiMessage.from(roleMsg.getContent()));
                    }
                }
            }
        }

        CompletableFuture<String> futureResponse = new CompletableFuture<>();
        chatLanguageModel.generate(messageList, new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
                for (StreamThoughtListener listener : listeners) {
                    listener.onNext(thoughtRequest, token);
                }
            }

            @Override
            public void onError(Throwable error) {
                futureResponse.completeExceptionally(error);
                for (StreamThoughtListener listener : listeners) {
                    listener.onError(thoughtRequest, error);
                }
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                StreamingResponseHandler.super.onComplete(response);
                AiMessage aiMessage = response.content();
                futureResponse.complete(aiMessage.text());
                for (StreamThoughtListener listener : listeners) {
                    listener.onComplete(thoughtRequest);
                }
            }
        });
        return futureResponse;
    }

}
