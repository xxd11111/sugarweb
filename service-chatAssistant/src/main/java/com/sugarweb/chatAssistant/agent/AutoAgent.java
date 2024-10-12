package com.sugarweb.chatAssistant.agent;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.agent.ability.*;
import com.sugarweb.chatAssistant.domain.po.AgentInfo;
import com.sugarweb.chatAssistant.domain.po.MessageInfo;
import com.sugarweb.chatAssistant.domain.po.PromptInfo;
import dev.langchain4j.data.message.AiMessage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private PromptInfo promptInfo;

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
                    }
                }
                ThinkContent thinkContent = new ThinkContent();
                thinkContent.setHistoryMessage(new ArrayList<>());
                //todo 处理
                // thinkContent.setCurrentQuestion(contextVariables);
                // thinkContent.setSystemMessage(question.toString());

                contextVariables.put("question", question.toString());

                //获取相关召回文档
                String retrievalSegment = memoryAbility.getRetrievalSegment(question.toString());
                if (StrUtil.isEmpty(retrievalSegment)) {
                    retrievalSegment = "无";
                }
                contextVariables.put("documents", retrievalSegment);


                // AiMessage aiMessage = response.content();
                // String aiMessageText = aiMessage.text();
                // //保存对话消息
                // MessageInfo userMessageInfo = createChatMessageInfo("user", question, memoryId);
                // MessageInfo aiMessageInfo = createChatMessageInfo("ai", aiMessageText, memoryId);
                // List<MessageInfo> batchSaveList = new ArrayList<>();
                // batchSaveList.add(userMessageInfo);
                // batchSaveList.add(aiMessageInfo);
                // Db.saveBatch(batchSaveList);


                if (!msgList.isEmpty()) {
                    StreamThinkSpeakAdapt streamThinkSpeakAdapt = new StreamThinkSpeakAdapt(chatId, speakAbility);
                    thinkAbility.streamThink(thinkContent, streamThinkSpeakAdapt);
                }
            }
        });
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
