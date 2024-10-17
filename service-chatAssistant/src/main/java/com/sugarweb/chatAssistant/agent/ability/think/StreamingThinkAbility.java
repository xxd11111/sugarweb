package com.sugarweb.chatAssistant.agent.ability.think;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.agent.EnvironmentInfo;
import com.sugarweb.chatAssistant.agent.ability.adaptor.ThinkInputAdaptor;
import com.sugarweb.chatAssistant.agent.ability.adaptor.ThinkOutputAdaptor;
import com.sugarweb.chatAssistant.agent.ability.input.blbl.BlblMsgPrompt;
import com.sugarweb.chatAssistant.agent.ability.memory.MemoryAbility;
import com.sugarweb.chatAssistant.agent.constans.ChatRole;
import com.sugarweb.chatAssistant.domain.BlblUser;
import com.sugarweb.chatAssistant.domain.ChatMsg;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 思考能力是协调各个能力的中心
 *
 * @author xxd
 * @version 1.0
 */
public class StreamingThinkAbility {

    private final ExecutorService executor;

    private Future<?> runningThread = null;

    private final EnvironmentInfo envInfo;

    private final ThinkInputAdaptor inputAdaptor;

    private final ThinkOutputAdaptor outputAdaptor;
    @Resource
    private MemoryAbility memoryAbility;
    @Resource
    private StreamingChatLanguageModel chatLanguageModel;
    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;


    public StreamingThinkAbility(ExecutorService executor, EnvironmentInfo envInfo, ThinkInputAdaptor inputAdaptor, ThinkOutputAdaptor outputAdaptor) {
        this.envInfo = envInfo;
        this.executor = executor;
        this.inputAdaptor = inputAdaptor;
        this.outputAdaptor = outputAdaptor;
    }

    public void start() {
        runningThread = executor.submit(() -> {
            long lastTime = System.currentTimeMillis();
            while (!Thread.currentThread().isInterrupted()) {
                Map<String, Object> contextVariables = new HashMap<>();

                // 限制最快100ms 思考一次
                limitThinkSpeed(lastTime, 100);
                lastTime = System.currentTimeMillis();
                long thinkId = System.currentTimeMillis();
                // 从消息队列中获取弹幕消息
                Object blblMsg = inputAdaptor.poll();
                BlblUser blblUser = BlblMsgPrompt.getBlblUserByMsg(blblMsg);
                contextVariables.put("user", blblUser);
                String question = BlblMsgPrompt.getMsgPrompt(blblMsg);
                contextVariables.put("question", question);

                //获取相关召回文档
                String documents = "无";
                //暂时不使用
                // String retrievalSegment = memoryAbility.getRetrievalSegment(question.toString());
                // if (StrUtil.isNotEmpty(retrievalSegment)) {
                //     documents = retrievalSegment;
                // }
                contextVariables.put("documents", documents);

                ThinkContent thinkContent = new ThinkContent();
                thinkContent.setThinkId(thinkId);
                // 系统提示语
                String systemPrompt = envInfo.getSystemPrompt(contextVariables);
                ChatMsg systemChatMsg = ChatMsg.of(ChatRole.SYSTEM, systemPrompt, envInfo.getCurrentMemoryId());
                thinkContent.setSystemMessage(systemChatMsg);
                Db.save(systemChatMsg);
                // 历史消息
                List<ChatMsg> chatMsgs = memoryAbility.listLastChatMessage(envInfo.getCurrentMemoryId(), 10);
                thinkContent.setHistoryMessage(chatMsgs);
                // 用户提问
                String userPrompt = envInfo.getUserPrompt(contextVariables);
                ChatMsg userChatMsg = ChatMsg.of(ChatRole.USER, userPrompt, envInfo.getCurrentMemoryId());
                thinkContent.setCurrentQuestion(userChatMsg);
                Db.save(userChatMsg);

                streamThink(thinkContent);
            }
        });
    }

    private StreamingResponseHandler<AiMessage> multiHandler(long thinkId) {
        StreamingResponseHandler<AiMessage> outputHandler = outputAdaptor.streamingOutputHandler(thinkId);
        StreamingResponseHandler<AiMessage> storeMemoryHandler = storeMemoryHandler();
        return new StreamingResponseHandler<>() {
            @Override
            public void onComplete(Response<AiMessage> response) {
                outputHandler.onComplete(response);
                storeMemoryHandler.onComplete(response);
            }

            @Override
            public void onNext(String token) {
                outputHandler.onNext(token);
                storeMemoryHandler.onNext(token);
            }

            @Override
            public void onError(Throwable error) {
                outputHandler.onError(error);
                storeMemoryHandler.onError(error);
            }
        };
    }

    @NotNull
    private StreamingResponseHandler<AiMessage> storeMemoryHandler() {
        //保存对话消息
        return new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
            }

            @Override
            public void onError(Throwable error) {
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                AiMessage aiMessage = response.content();
                String aiMessageText = aiMessage.text();
                //保存对话消息
                ChatMsg aiMessageInfo = ChatMsg.of(ChatRole.ASSISTANT, aiMessageText, envInfo.getCurrentMemoryId());
                memoryAbility.saveChatMessage(aiMessageInfo);

            }
        };
    }


    private void limitThinkSpeed(long lastTime, long limit) {
        long now = System.currentTimeMillis();
        if (now - lastTime < limit) {
            ThreadUtil.sleep(now - lastTime);
        }
    }


    public void streamThink(ThinkContent thinkContent) {
        //组装发送给大模型的消息
        List<ChatMessage> messageList = new ArrayList<>();
        //第一步，配置系统消息
        ChatMsg systemChatMsg = thinkContent.getSystemMessage();
        if (systemChatMsg != null) {
            ChatMessage systemMessage = new SystemMessage(systemChatMsg.getContent());
            messageList.add(systemMessage);
        }

        //第二步，获取ai对话历史消息
        List<ChatMsg> historyMessage = thinkContent.getHistoryMessage();
        if (historyMessage != null && !historyMessage.isEmpty()) {
            for (ChatMsg chatMsg : historyMessage) {
                ChatMessage chatMessage = convertToLangChain4jMsg(chatMsg);
                messageList.add(chatMessage);
            }
        }

        //第三步，获取当前提问的消息
        String question = thinkContent.getCurrentQuestion().getContent();
        messageList.add(new UserMessage(question));

        chatLanguageModel.generate(messageList, multiHandler(thinkContent.getThinkId()));

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
        if (runningThread != null) {
            runningThread.cancel(true);
        }
    }


}
