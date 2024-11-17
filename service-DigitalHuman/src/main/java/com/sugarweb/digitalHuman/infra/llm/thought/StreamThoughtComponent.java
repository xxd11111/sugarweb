package com.sugarweb.digitalHuman.infra.llm.thought;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sugarweb.digitalHuman.constants.ChatRole;
import com.sugarweb.digitalHuman.domain.*;
import com.sugarweb.digitalHuman.infra.PromptUtil;
import com.sugarweb.digitalHuman.infra.llm.StageContext;
import com.sugarweb.digitalHuman.infra.llm.input.InputContainer;
import com.sugarweb.digitalHuman.infra.llm.input.blbl.BlblMsgPrompt;
import com.sugarweb.digitalHuman.infra.llm.memory.DatasetMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.memory.PerformanceMemoryComponent;
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
public class StreamThoughtComponent {

    private final ExecutorService executor;

    private Future<?> thinkThread = null;

    private final InputContainer inputContainer;

    private final DatasetMemoryComponent datasetMemoryComponent;

    private final PerformanceMemoryComponent performanceMemoryComponent;

    private final StreamingChatLanguageModel chatLanguageModel;

    private final List<StreamListener> listeners;

    private final Actor actor;
    private final Script script;

    @Builder
    public StreamThoughtComponent(StageContext stageContext, InputContainer inputContainer, DatasetMemoryComponent datasetMemoryComponent, PerformanceMemoryComponent performanceMemoryComponent, List<StreamListener> listeners) {
        this.executor = stageContext.getExecutor();
        this.inputContainer = inputContainer;
        this.datasetMemoryComponent = datasetMemoryComponent;
        this.performanceMemoryComponent = performanceMemoryComponent;
        this.chatLanguageModel = stageContext.getChatLanguageModel();
        this.listeners = listeners;
        this.actor = stageContext.getActor();
        this.script = stageContext.getScript();
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
                        next(thinkId);
                    }
                } catch (Exception e) {
                    log.error("ai思考异常 error:{}", e.getMessage(), e);
                }
            }
        });
    }

    public void next(long thinkId) {
        Script script = new Script();
        List<ScriptNode> scriptNodeList = script.getScriptNodeList();
        //todo
    }

    public boolean shouldAnswer() {
        //todo
        return true;
    }

    public void answer(long thinkId) {
        // 从消息队列中获取弹幕消息
        Object blblMsg = inputContainer.poll();
        if (blblMsg == null) {
            return;
        }
        ThoughtContext thoughtContext = new ThoughtContext();
        StagePerformanceMsg performanceMsg = new StagePerformanceMsg();
        performanceMsg.setStartTime(LocalDateTime.now());

        BlblUser blblUser = BlblMsgPrompt.getBlblUserByMsg(blblMsg);
        thoughtContext.put("user", blblUser);
        String question = BlblMsgPrompt.getMsgPrompt(blblMsg);
        thoughtContext.put("question", question);
        // 用户提问
        thoughtContext.setQuestionMsg(question);

        //获取相关召回文档
        String documents = "无";
        if (datasetMemoryComponent != null) {
            String retrievalSegment = datasetMemoryComponent.getRetrievalSegment(question);
            if (StrUtil.isNotEmpty(retrievalSegment)) {
                documents = retrievalSegment;
            }
        }
        //todo rerank
        thoughtContext.put("documents", documents);

        thoughtContext.setThoughtId(thinkId);
        // 系统提示语
        String systemPrompt = PromptUtil.getPrompt(actor.getPromptTemplate(), actor.getPromptVariables(), thoughtContext.getContextVariables());
        thoughtContext.setSystemMsg(systemPrompt);

        // 历史消息
        StagePerformanceMsg lastUserMsg = performanceMemoryComponent.lastUserMsg(performanceMsg.getPerformanceId(), blblUser.getBlblUid());
        thoughtContext.setHistoryMsg(lastUserMsg);

        streamThink(thoughtContext);
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
                for (StreamListener listener : listeners) {
                    listener.onNext(thoughtContext, token);
                }
            }

            @Override
            public void onError(Throwable error) {
                for (StreamListener listener : listeners) {
                    listener.onError(thoughtContext, error);
                }
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                StreamingResponseHandler.super.onComplete(response);
                AiMessage aiMessage = response.content();
                thoughtContext.setAssistantMsg(aiMessage.text());
                for (StreamListener listener : listeners) {
                    listener.onComplete(thoughtContext);
                }
            }
        });
    }


    @Data
    @AllArgsConstructor
    public static class RoleMsg {

        private String role;

        private String content;

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

    public void stop() {
        if (thinkThread != null) {
            thinkThread.cancel(true);
        }
    }

}
