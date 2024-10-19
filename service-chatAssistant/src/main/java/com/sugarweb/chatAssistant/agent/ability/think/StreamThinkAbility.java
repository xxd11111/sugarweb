package com.sugarweb.chatAssistant.agent.ability.think;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.agent.EnvironmentInfo;
import com.sugarweb.chatAssistant.agent.ability.input.InputContainer;
import com.sugarweb.chatAssistant.agent.ability.input.blbl.BlblMsgPrompt;
import com.sugarweb.chatAssistant.agent.ability.memory.MemoryAbility;
import com.sugarweb.chatAssistant.constans.ChatRole;
import com.sugarweb.chatAssistant.domain.BlblUser;
import com.sugarweb.chatAssistant.domain.ChatMsg;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 思考能力是协调各个能力的中心
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class StreamThinkAbility {

    private final ExecutorService executor;

    private Future<?> thinkThread = null;

    private final EnvironmentInfo envInfo;

    private final InputContainer inputContainer;

    private final MemoryAbility memoryAbility;

    //todo 应该根据envInfo构建大模型调用工具
    private final StreamingChatLanguageModel chatLanguageModel;

    private final List<StreamListener> listeners;

    public StreamThinkAbility(ExecutorService executor, EnvironmentInfo envInfo, InputContainer inputContainer, MemoryAbility memoryAbility, StreamingChatLanguageModel chatLanguageModel, List<StreamListener> listeners) {
        this.envInfo = envInfo;
        this.executor = executor;
        this.inputContainer = inputContainer;
        this.memoryAbility = memoryAbility;
        this.chatLanguageModel = chatLanguageModel;
        this.listeners = listeners;
    }

    public void start() {
        if (thinkThread != null && !thinkThread.isDone()) {
            return;
        }

        thinkThread = executor.submit(() -> {
            SpeedLimiter speedLimiter = new SpeedLimiter(0);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 限制最快100ms 思考一次
                    speedLimiter.limit(100);
                    long thinkId = System.currentTimeMillis();
                    // 从消息队列中获取弹幕消息
                    Object blblMsg = inputContainer.poll();
                    if (blblMsg == null) {
                        continue;
                    }

                    ThinkContext thinkContext = new ThinkContext();
                    thinkContext.setStartTime(LocalDateTime.now());

                    BlblUser blblUser = BlblMsgPrompt.getBlblUserByMsg(blblMsg);
                    thinkContext.put("user", blblUser);
                    String question = BlblMsgPrompt.getMsgPrompt(blblMsg);
                    thinkContext.put("question", question);

                    //获取相关召回文档
                    String documents = "无";
                    //暂时不使用
                    // String retrievalSegment = memoryAbility.getRetrievalSegment(question.toString());
                    // if (StrUtil.isNotEmpty(retrievalSegment)) {
                    //     documents = retrievalSegment;
                    // }
                    thinkContext.put("documents", documents);

                    thinkContext.setThinkId(thinkId);
                    thinkContext.setMemoryId(envInfo.getCurrentMemoryId());
                    // 系统提示语
                    String systemPrompt = envInfo.getSystemPrompt(thinkContext.getContextVariables());
                    ChatMsg systemChatMsg = ChatMsg.of(ChatRole.SYSTEM, systemPrompt, envInfo.getCurrentMemoryId(), blblUser.getBlblUid());
                    thinkContext.setSystemMsg(systemChatMsg);
                    // 历史消息
                    List<ChatMsg> chatMsgs = memoryAbility.listLastChatMessage(envInfo.getCurrentMemoryId(), blblUser.getBlblUid(), 10);
                    thinkContext.setHistoryMsgList(chatMsgs);
                    // 用户提问
                    String userPrompt = envInfo.getUserPrompt(thinkContext.getContextVariables());
                    ChatMsg userChatMsg = ChatMsg.of(ChatRole.USER, userPrompt, envInfo.getCurrentMemoryId(), blblUser.getBlblUid());
                    thinkContext.setQuestionMsg(userChatMsg);
                    streamThink(thinkContext);
                } catch (Exception e) {
                    log.error("ai思考异常 error:{}", e);
                }
            }
        });
    }

    public static class SpeedLimiter {
        private long lastTime;

        public SpeedLimiter(long lastTime) {
            this.lastTime = lastTime;
        }

        public void limit(long limit) {
            long now = System.currentTimeMillis();
            if (now - lastTime < limit) {
                ThreadUtil.sleep(now - lastTime);
            }
            lastTime = System.currentTimeMillis();
        }
    }

    public void streamThink(ThinkContext thinkContext) {
        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        ChatMsg systemChatMsg = thinkContext.getSystemMsg();
        if (systemChatMsg != null) {
            ChatMessage systemMessage = new SystemMessage(systemChatMsg.getContent());
            messageList.add(systemMessage);
        }

        //第二步，获取ai对话历史消息
        List<ChatMsg> historyMessage = thinkContext.getHistoryMsgList();
        if (historyMessage != null && !historyMessage.isEmpty()) {
            for (ChatMsg chatMsg : historyMessage) {
                ChatMessage chatMessage = convertToLangChain4jMsg(chatMsg);
                messageList.add(chatMessage);
            }
        }
        //第三步，获取当前提问的消息
        String question = thinkContext.getQuestionMsg().getContent();
        messageList.add(new UserMessage(question));


        chatLanguageModel.generate(messageList, new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
                for (StreamListener listener : listeners) {
                    listener.onNext(thinkContext, token);
                }
            }

            @Override
            public void onError(Throwable error) {
                for (StreamListener listener : listeners) {
                    listener.onError(thinkContext, error);
                }
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                thinkContext.setEndTime(LocalDateTime.now());
                log.info("thinkId:{},推理耗时；{}毫秒", thinkContext.getThinkId(), Duration.between(thinkContext.getStartTime(), thinkContext.getEndTime()).toMillis());
                StreamingResponseHandler.super.onComplete(response);
                AiMessage aiMessage = response.content();
                ChatMsg aiChatMsg = ChatMsg.of(ChatRole.ASSISTANT, aiMessage.text(), envInfo.getCurrentMemoryId(), thinkContext.getBlblUid());
                thinkContext.setAssistantMsg(aiChatMsg);
                for (StreamListener listener : listeners) {
                    listener.onComplete(thinkContext);
                }
            }
        });
    }


    private ChatMessage convertToLangChain4jMsg(ChatMsg chatMsg) {
        if (ChatRole.USER.getCode().equals(chatMsg.getChatRole())) {
            return new UserMessage(chatMsg.getContent());
        } else if (ChatRole.ASSISTANT.getCode().equals(chatMsg.getChatRole())) {
            return new AiMessage(chatMsg.getContent());
        } else if (ChatRole.SYSTEM.getCode().equals(chatMsg.getChatRole())) {
            return new SystemMessage(chatMsg.getContent());
        }
        throw new IllegalArgumentException(StrUtil.format("不支持的消息类型,messageId:{}", chatMsg.getMsgId()));
    }

    public void stop() {
        if (thinkThread != null) {
            thinkThread.cancel(true);
        }
    }

}
