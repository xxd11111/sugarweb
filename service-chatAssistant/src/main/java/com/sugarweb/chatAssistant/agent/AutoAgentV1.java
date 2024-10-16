package com.sugarweb.chatAssistant.agent;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.agent.ability.aware.BlblMsgAdaptor;
import com.sugarweb.chatAssistant.agent.ability.aware.BlblMsgAwareAbility;
import com.sugarweb.chatAssistant.agent.ability.memory.MemoryAbility;
import com.sugarweb.chatAssistant.agent.ability.speak.SpeakAbility;
import com.sugarweb.chatAssistant.agent.ability.speak.StreamThinkSpeakAdapt;
import com.sugarweb.chatAssistant.agent.ability.think.ThinkAbility;
import com.sugarweb.chatAssistant.agent.ability.think.ThinkContent;
import com.sugarweb.chatAssistant.agent.constans.ChatRole;
import com.sugarweb.chatAssistant.domain.*;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.output.Response;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 自动智能助手
 *
 * @author xxd
 * @version 1.0
 */
public class AutoAgentV1 {
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private SpeakAbility speakAbility;
    private ThinkAbility thinkAbility;
    private MemoryAbility memoryAbility;
    private BlblMsgAwareAbility blblMsgAwareAbility;
    private Future<?> runningThread = null;

    private AgentInfo agentInfo;
    private PromptTemplateInfo userPromptTemplateInfo;
    private PromptTemplateInfo systemPromptTemplateInfo;
    private StageInfo stageInfo;
    private SceneInfo sceneInfo;
    private AgentMemory agentMemory;


    public void init() {
        speakAbility = new SpeakAbility(executor);
        speakAbility.init();
        thinkAbility = new ThinkAbility();
        memoryAbility = new MemoryAbility();
        blblMsgAwareAbility = new BlblMsgAwareAbility();
        blblMsgAwareAbility.init();
    }


    public void start() {
        speakAbility.start();
        blblMsgAwareAbility.start();
        thinkAbility.start();

    }



    public void stop() {
        if (runningThread != null) {
            runningThread.interrupt();
        }
    }


}
