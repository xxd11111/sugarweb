package com.sugarweb.digitalHuman.infra.agent.component.think;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.digitalHuman.infra.agent.EnvironmentContext;
import com.sugarweb.digitalHuman.infra.agent.component.input.InputContainer;
import com.sugarweb.digitalHuman.infra.agent.component.input.blbl.BlblMsgPrompt;
import com.sugarweb.digitalHuman.infra.agent.component.memory.MemoryComponent;
import com.sugarweb.digitalHuman.constans.ChatRole;
import com.sugarweb.digitalHuman.domain.*;
import com.sugarweb.digitalHuman.infra.llm.ModelFactory;
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
import java.util.HashMap;
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
public class StreamThinkComponent {

    private final ExecutorService executor;

    private Future<?> thinkThread = null;

    private final InputContainer inputContainer;

    private final MemoryComponent memoryComponent;

    private final StreamingChatLanguageModel chatLanguageModel;

    private final List<StreamListener> listeners;

    private final PromptTemplateInfo answerSystemPrompt;

    private final PromptTemplateInfo topicSystemPrompt;

    public StreamThinkComponent(EnvironmentContext environmentContext, InputContainer inputContainer, MemoryComponent memoryComponent, List<StreamListener> listeners) {
        this.executor = environmentContext.getExecutor();
        this.inputContainer = inputContainer;
        this.memoryComponent = memoryComponent;
        AgentInfo agentInfo = environmentContext.getAgentInfo();
        this.answerSystemPrompt = agentInfo.getSystemPrompt();
        ModelInfo chatModelInfo = agentInfo.getChatModelInfo();
        this.chatLanguageModel = ModelFactory.creatStreamingChatLanguageModel(chatModelInfo);
        this.listeners = listeners;
        //todo 设置聊天主题提示词
        this.topicSystemPrompt = new PromptTemplateInfo();
    }

    public void start() {
        if (thinkThread != null && !thinkThread.isDone()) {
            return;
        }

        thinkThread = executor.submit(() -> {
            SpeedLimiter speedLimiter = new SpeedLimiter(0);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 限制最快1000ms 思考一次
                    speedLimiter.limit(1000);
                    long thinkId = System.currentTimeMillis();
                    //判断是否需要回答
                    if (shouldAnswer()) {
                        //响应用户问题
                        answer(thinkId);
                    } else {
                        //继续下个话题
                        nextTopic(thinkId);
                    }
                } catch (Exception e) {
                    log.error("ai思考异常 error:{}", e.getMessage(), e);
                }
            }
        });
    }

    public void nextTopic(long thinkId) {
        String prompt = topicSystemPrompt.getPrompt(new HashMap<>());
    }

    public boolean shouldAnswer() {
        //todo
        return false;
    }

    public void answer(long thinkId) {
        // 从消息队列中获取弹幕消息
        Object blblMsg = inputContainer.poll();
        if (blblMsg == null) {
            return;
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
        // 系统提示语
        String systemPrompt = answerSystemPrompt.getPrompt(thinkContext.getContextVariables());
        ChatMsg systemChatMsg = ChatMsg.of(ChatRole.SYSTEM, systemPrompt, blblUser.getBlblUid());
        thinkContext.setSystemMsg(systemChatMsg);
        // 历史消息
        List<ChatMsg> chatMsgs = memoryComponent.lastChatMessage(blblUser.getBlblUid(), 10);
        thinkContext.setHistoryMsgList(chatMsgs);
        // 用户提问
        ChatMsg userChatMsg = ChatMsg.of(ChatRole.USER, question, blblUser.getBlblUid());
        thinkContext.setQuestionMsg(userChatMsg);
        streamThink(thinkContext);
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
                ChatMsg aiChatMsg = ChatMsg.of(ChatRole.ASSISTANT, aiMessage.text(), thinkContext.getBlblUid());
                thinkContext.setAssistantMsg(aiChatMsg);
                for (StreamListener listener : listeners) {
                    listener.onComplete(thinkContext);
                }
            }
        });
    }


    private ChatMessage convertToLangChain4jMsg(ChatMsg chatMsg) {
        if (ChatRole.USER.getValue().equals(chatMsg.getChatRole())) {
            return new UserMessage(chatMsg.getContent());
        } else if (ChatRole.ASSISTANT.getValue().equals(chatMsg.getChatRole())) {
            return new AiMessage(chatMsg.getContent());
        } else if (ChatRole.SYSTEM.getValue().equals(chatMsg.getChatRole())) {
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
