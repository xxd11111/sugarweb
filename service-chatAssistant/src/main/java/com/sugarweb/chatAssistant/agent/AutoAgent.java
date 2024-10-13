package com.sugarweb.chatAssistant.agent;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.agent.ability.*;
import com.sugarweb.chatAssistant.domain.po.*;
import com.sun.nio.sctp.MessageInfo;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private PromptInfo userPromptInfo;
    private PromptInfo systemPromptInfo;
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

    private String assemblyBlblMsg(Object o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (o instanceof BlblDmMsg dmMsg) {
            return StrUtil.format("时间：{}，收到一条互动消息，用户：{}，内容：{}\n", dmMsg.getTime().format(formatter), dmMsg.getUsername(), dmMsg.getContent());
        } else if (o instanceof BlblGiftMsg giftMsg) {
            return StrUtil.format("时间：{}，收到用户送的礼物，用户：{}，礼物名：{}，价格：{}，数量：{}\n", giftMsg.getTime().format(formatter), giftMsg.getUsername(), giftMsg.getGifyName(), giftMsg.getGiftPrice(), giftMsg.getGiftNum());
        } else if (o instanceof BlblLikeMsg likeMsg) {
            return StrUtil.format("时间：{}，收到用户点赞，用户：{}，点赞量：{}\n", likeMsg.getTime().format(formatter), likeMsg.getUsername(), likeMsg.getLikeNum());
        } else if (o instanceof BlblEnterRoomMsg enterRoomMsg) {
            return StrUtil.format("时间：{}，用户进入房间，用户：{}\n", enterRoomMsg.getTime().format(formatter), enterRoomMsg.getUsername());
        } else if (o instanceof BlblCountMsg countMsg) {
            return StrUtil.format("时间：{}，房价定时统计，累计观看人数：{}，正在观看人数：{}，累计点赞数：{}\n", countMsg.getTime().format(formatter), countMsg.getWatchedCount(), countMsg.getWatchingCount(), countMsg.getLikeCount());
        }
        return "";
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
                long chatId = System.currentTimeMillis();
                // 从消息队列中获取消息
                List<Object> msgList = blblMsgAwareAbility.consumeMsg();
                StringBuilder question = new StringBuilder();
                for (Object msg : msgList) {
                    String content = assemblyBlblMsg(msg);
                    if (StrUtil.isNotEmpty(content)){
                        question.append(content);
                        contextVariables.put("question", question.toString());
                    }
                }
                PromptTemplate userPromptTemplate = new PromptTemplate(userPromptInfo.getContent());
                Prompt userPrompt = userPromptTemplate.apply(contextVariables);
                String userPromptText = userPrompt.text();

                ThinkContent thinkContent = new ThinkContent();
                List<ChatMessageInfo> chatMessageInfos = memoryAbility.listLastChatMessage(agentMemory.getMemoryId(), 10);
                thinkContent.setHistoryMessage(chatMessageInfos);
                ChatMessageInfo userChatMessageInfo = new ChatMessageInfo();
                userChatMessageInfo.setChatId(chatId+"");
                userChatMessageInfo.setMemoryId(agentMemory.getMemoryId());
                userChatMessageInfo.setContent(userPromptText);
                userChatMessageInfo.setChatRole("user");
                userChatMessageInfo.setCreateTime(LocalDateTime.now());
                userChatMessageInfo.setUpdateTime(LocalDateTime.now());
                thinkContent.setCurrentQuestion(userChatMessageInfo);

                //获取相关召回文档
                String documents = "无";
                //暂时不使用
                // String retrievalSegment = memoryAbility.getRetrievalSegment(question.toString());
                // if (StrUtil.isNotEmpty(retrievalSegment)) {
                //     documents = retrievalSegment;
                // }
                contextVariables.put("documents", documents);

                ChatMessageInfo systemChatMessageInfo = new ChatMessageInfo();
                systemChatMessageInfo.setChatId(chatId+"");
                systemChatMessageInfo.setMemoryId(agentMemory.getMemoryId());
                systemChatMessageInfo.setContent(systemPromptInfo.getContent());
                systemChatMessageInfo.setChatRole("system");
                systemChatMessageInfo.setCreateTime(LocalDateTime.now());
                systemChatMessageInfo.setUpdateTime(LocalDateTime.now());
                thinkContent.setSystemMessage(systemChatMessageInfo);

                if (!msgList.isEmpty()) {
                    StreamingResponseHandler<AiMessage> streamingResponseHandler = getStreamingResponseHandler(chatId);
                    thinkAbility.streamThink(thinkContent, streamingResponseHandler);
                }
            }
        });
    }

    @NotNull
    private StreamingResponseHandler<AiMessage> getStreamingResponseHandler(long chatId) {
        StreamThinkSpeakAdapt streamThinkSpeakAdapt = new StreamThinkSpeakAdapt(chatId, speakAbility);
        //保存对话消息
        return new StreamingResponseHandler<AiMessage>(){
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
                ChatMessageInfo aiMessageInfo = createChatMessageInfo("ai", aiMessageText, agentMemory.getMemoryId());
                memoryAbility.saveChatMessage(aiMessageInfo);

                streamThinkSpeakAdapt.onComplete(response);
            }
        };
    }

    private ChatMessageInfo createChatMessageInfo(String type, String question, String memoryId) {
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setMemoryId(memoryId);
        chatMessageInfo.setContent(question);
        chatMessageInfo.setChatRole(type);
        chatMessageInfo.setCreateTime(LocalDateTime.now());
        chatMessageInfo.setUpdateTime(LocalDateTime.now());
        return chatMessageInfo;
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
