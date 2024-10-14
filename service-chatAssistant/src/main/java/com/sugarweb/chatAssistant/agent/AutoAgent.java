package com.sugarweb.chatAssistant.agent;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.agent.ability.*;
import com.sugarweb.chatAssistant.domain.po.*;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自动智能助手
 *
 * @author xxd
 * @version 1.0
 */
public class AutoAgent {
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private SpeakAbility speakAbility;
    private ThinkAbility thinkAbility;
    private MemoryAbility memoryAbility;
    private BlblMsgAwareAbility blblMsgAwareAbility;
    private Thread runningThread = null;

    private AgentInfo agentInfo;
    private PromptTemplateInfo userPromptTemplateInfo;
    private PromptTemplateInfo systemPromptTemplateInfo;
    private StageInfo stageInfo;
    private SceneInfo sceneInfo;
    private AgentMemory agentMemory;


    public void init() {
        speakAbility = new SpeakAbility(executor);
        thinkAbility = new ThinkAbility();
        memoryAbility = new MemoryAbility();
        blblMsgAwareAbility = new BlblMsgAwareAbility();
        speakAbility.init();
        blblMsgAwareAbility.init();
    }


    public void start() {
        speakAbility.start();
        blblMsgAwareAbility.start();
        runningThread = Thread.startVirtualThread(() -> {
            long lastTime = System.currentTimeMillis();
            while (!Thread.currentThread().isInterrupted()) {
                Map<String, String> contextVariables = new HashMap<>();

                // 限制最快100ms 思考一次
                limitThinkSpeed(lastTime, 100);
                lastTime = System.currentTimeMillis();
                long thinkId = System.currentTimeMillis();
                // 从消息队列中获取消息
                List<Object> msgList = blblMsgAwareAbility.consumeMsg();
                String question = BlblMsgAdaptor.assemblyMultiMsg(msgList);
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
                String systemPrompt = systemPromptTemplateInfo.getPrompt(contextVariables);
                ChatMessageInfo systemChatMessageInfo = new ChatMessageInfo();
                systemChatMessageInfo.setMemoryId(agentMemory.getMemoryId());
                systemChatMessageInfo.setContent(systemPrompt);
                systemChatMessageInfo.setChatRole(ChatRole.SYSTEM.getCode());
                systemChatMessageInfo.setCreateTime(LocalDateTime.now());
                systemChatMessageInfo.setUpdateTime(LocalDateTime.now());
                thinkContent.setSystemMessage(systemChatMessageInfo);

                List<ChatMessageInfo> chatMessageInfos = memoryAbility.listLastChatMessage(agentMemory.getMemoryId(), 10);
                thinkContent.setHistoryMessage(chatMessageInfos);

                String userPrompt = userPromptTemplateInfo.getPrompt(contextVariables);
                ChatMessageInfo userChatMessageInfo = new ChatMessageInfo();
                userChatMessageInfo.setMemoryId(agentMemory.getMemoryId());
                userChatMessageInfo.setContent(userPrompt);
                userChatMessageInfo.setChatRole(ChatRole.USER.getCode());
                userChatMessageInfo.setCreateTime(LocalDateTime.now());
                userChatMessageInfo.setUpdateTime(LocalDateTime.now());
                thinkContent.setCurrentQuestion(userChatMessageInfo);
                Db.save(userChatMessageInfo);

                if (!msgList.isEmpty()) {
                    StreamingResponseHandler<AiMessage> streamingResponseHandler = getStreamingResponseHandler(thinkId);
                    thinkAbility.streamThink(thinkContent, streamingResponseHandler);
                }
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
                ChatMessageInfo aiMessageInfo = ChatMessageInfo.of(ChatRole.ASSISTANT, aiMessageText, agentMemory.getMemoryId());
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

    public void stop() {
        if (runningThread != null) {
            runningThread.interrupt();
        }
    }


}
