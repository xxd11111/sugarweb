package com.sugarweb.chatAssistant.agent;

import com.sugarweb.chatAssistant.agent.ability.adaptor.ThinkInputAdaptor;
import com.sugarweb.chatAssistant.agent.ability.adaptor.ThinkOutputAdaptor;
import com.sugarweb.chatAssistant.agent.ability.input.blbl.BlblMsgInputAbility;
import com.sugarweb.chatAssistant.agent.ability.memory.MemoryAbility;
import com.sugarweb.chatAssistant.agent.ability.output.speak.SpeakOutputAbility;
import com.sugarweb.chatAssistant.agent.ability.think.StreamingThinkAbility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;

/**
 * 自动智能助手
 *
 * @author xxd
 * @version 1.0
 */
@AllArgsConstructor
public class AutoAgent {

    private final ExecutorService executor;
    private final boolean isRunning = false;
    private final EnvironmentInfo environmentInfo;
    //装载的能力
    private final StreamingThinkAbility streamingThinkAbility;
    private final SpeakOutputAbility speakOutputAbility;
    private final MemoryAbility memoryAbility;
    private final BlblMsgInputAbility blblMsgInputAbility;

    public AutoAgent(ExecutorService executor, EnvironmentInfo environmentInfo) {
        this.executor = executor;
        this.environmentInfo = environmentInfo;

        //创建输入适配器
        ThinkInputAdaptor thinkInputAdaptor = new ThinkInputAdaptor();
        //创建输出适配器
        ThinkOutputAdaptor thinkOutputAdaptor = new ThinkOutputAdaptor(executor);
        //装载输出能力
        speakOutputAbility = new SpeakOutputAbility(executor, thinkOutputAdaptor);
        //装载记忆能力
        memoryAbility = new MemoryAbility();

        //装载输入能力
        blblMsgInputAbility = new BlblMsgInputAbility(thinkInputAdaptor);
        //装载思考能力
        streamingThinkAbility = new StreamingThinkAbility(executor, environmentInfo, thinkInputAdaptor, thinkOutputAdaptor);
    }

    public void start() {
        speakOutputAbility.start();
        blblMsgInputAbility.start();
        streamingThinkAbility.start();
    }


    public void stop() {
        speakOutputAbility.stop();
        blblMsgInputAbility.stop();
        streamingThinkAbility.stop();
        executor.shutdown();
    }

    public boolean isRunning() {
        return isRunning;
    }


}
