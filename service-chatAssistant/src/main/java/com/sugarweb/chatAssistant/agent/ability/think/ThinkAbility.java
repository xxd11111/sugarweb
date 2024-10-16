package com.sugarweb.chatAssistant.agent.ability.think;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.agent.ability.aware.BlblMsgAdaptor;
import com.sugarweb.chatAssistant.agent.ability.memory.MemoryAbility;
import com.sugarweb.chatAssistant.agent.ability.speak.SpeakAbility;
import com.sugarweb.chatAssistant.agent.ability.speak.StreamThinkSpeakAdapt;
import com.sugarweb.chatAssistant.agent.constans.ChatRole;
import com.sugarweb.chatAssistant.application.PromptService;
import com.sugarweb.chatAssistant.domain.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
public class ThinkAbility {

    private final ExecutorService executor;

    private final ThinkInputQueue inputQueue;

    private final SpeakAbility speakAbility;

    private final PromptTemplateInfo systemPromptTemplateInfo;

    private final PromptTemplateInfo userPromptTemplateInfo;

    private final AgentMemory agentMemory;

    @Resource
    private MemoryAbility memoryAbility;
    @Resource
    private StreamingChatLanguageModel chatLanguageModel;
    @Resource
    private EmbeddingModel embeddingModel;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    private Future<?> runningThread = null;

    public ThinkAbility(ExecutorService executor, ThinkInputQueue inputQueue, SpeakAbility speakAbility, PromptTemplateInfo systemPromptTemplateInfo, PromptTemplateInfo userPromptTemplateInfo, AgentMemory agentMemory) {
        this.executor = executor;
        this.inputQueue = inputQueue;
        this.speakAbility = speakAbility;
        this.systemPromptTemplateInfo = systemPromptTemplateInfo;
        this.userPromptTemplateInfo = userPromptTemplateInfo;
        this.agentMemory = agentMemory;
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
                Object blblMsg = inputQueue.poll();
                BlblUser blblUser = BlblMsgAdaptor.getBlblUserByMsg(blblMsg);
                contextVariables.put("user", blblUser);
                String question = BlblMsgAdaptor.getMsgPrompt(blblMsg);
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
                // 系统提示语
                String systemPrompt = systemPromptTemplateInfo.getPrompt(contextVariables);
                ChatMsg systemChatMsg = ChatMsg.of(ChatRole.SYSTEM, systemPrompt, agentMemory.getMemoryId());
                thinkContent.setSystemMessage(systemChatMsg);
                Db.save(systemChatMsg);
                // 历史消息
                List<ChatMsg> chatMsgs = memoryAbility.listLastChatMessage(agentMemory.getMemoryId(), 10);
                thinkContent.setHistoryMessage(chatMsgs);
                // 用户提问
                String userPrompt = userPromptTemplateInfo.getPrompt(contextVariables);
                ChatMsg userChatMsg = ChatMsg.of(ChatRole.USER, userPrompt, agentMemory.getMemoryId());
                thinkContent.setCurrentQuestion(userChatMsg);
                Db.save(userChatMsg);

                StreamingResponseHandler<AiMessage> streamingResponseHandler = getStreamingResponseHandler(thinkId);
                streamThink(thinkContent, streamingResponseHandler);
            }
        });
    }

    @NotNull
    private StreamingResponseHandler<AiMessage> getStreamingResponseHandler(long thinkId) {
        StreamThinkSpeakAdapt streamThinkSpeakAdapt = new StreamThinkSpeakAdapt(thinkId, speakAbility);
        //保存对话消息
        return new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
                streamThinkSpeakAdapt.onNext(token);
            }

            @Override
            public void onError(Throwable error) {
                streamThinkSpeakAdapt.onError(error);
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                AiMessage aiMessage = response.content();
                String aiMessageText = aiMessage.text();
                //保存对话消息
                ChatMsg aiMessageInfo = ChatMsg.of(ChatRole.ASSISTANT, aiMessageText, agentMemory.getMemoryId());
                memoryAbility.saveChatMessage(aiMessageInfo);

                streamThinkSpeakAdapt.onComplete(response);
            }
        };
    }


    private void limitThinkSpeed(long lastTime, long limit) {
        long now = System.currentTimeMillis();
        if (now - lastTime < limit) {
            ThreadUtil.sleep(now - lastTime);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void streamThink(ThinkContent thinkContent, StreamingResponseHandler<AiMessage> extHandler) {
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
                ChatMessage chatMessage = getChatMessage(chatMsg);
                messageList.add(chatMessage);
            }
        }

        //第三步，获取当前提问的消息
        String question = thinkContent.getCurrentQuestion().getContent();
        messageList.add(new UserMessage(question));

        chatLanguageModel.generate(messageList, extHandler);

    }


    private ChatMessage getChatMessage(ChatMsg chatMsg) {
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
