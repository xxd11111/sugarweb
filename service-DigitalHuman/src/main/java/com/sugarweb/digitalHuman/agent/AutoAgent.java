package com.sugarweb.digitalHuman.agent;

import com.sugarweb.digitalHuman.agent.ability.input.InputContainer;
import com.sugarweb.digitalHuman.agent.ability.input.blbl.BlblMsgInputAbility;
import com.sugarweb.digitalHuman.agent.ability.memory.MemoryAbility;
import com.sugarweb.digitalHuman.agent.ability.memory.MemoryOutputListener;
import com.sugarweb.digitalHuman.agent.ability.output.OutputContainer;
import com.sugarweb.digitalHuman.agent.ability.output.audio.AudioOutputAbility;
import com.sugarweb.digitalHuman.agent.ability.output.audio.AudioOutputListener;
import com.sugarweb.digitalHuman.agent.ability.think.StreamListener;
import com.sugarweb.digitalHuman.agent.ability.think.StreamThinkAbility;
import lombok.AllArgsConstructor;

import java.util.List;
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
    private boolean isRunning = false;
    private final EnvironmentInfo environmentInfo;
    //装载的能力
    private final StreamThinkAbility streamThinkAbility;
    private final AudioOutputAbility audioOutputAbility;
    private final MemoryAbility memoryAbility;
    private final BlblMsgInputAbility blblMsgInputAbility;

    public AutoAgent(ExecutorService executor, EnvironmentInfo environmentInfo) {
        this.executor = executor;
        this.environmentInfo = environmentInfo;

        //装载记忆能力
        memoryAbility = new MemoryAbility(environmentInfo.getEmbeddingModel(), environmentInfo.getEmbeddingStore());
        //创建记忆输出监听器
        StreamListener memoryOutputListener = new MemoryOutputListener(memoryAbility);
        //创建输入适配器
        InputContainer inputContainer = new InputContainer();

        OutputContainer outputContainer = new OutputContainer();
        //装载输出能力
        audioOutputAbility = new AudioOutputAbility(executor, outputContainer);
        //创建输出监听器
        StreamListener audioOutputListener = new AudioOutputListener(outputContainer);
        //装载输入能力
        blblMsgInputAbility = new BlblMsgInputAbility(inputContainer);

        List<StreamListener> streamListeners = List.of(memoryOutputListener, audioOutputListener);
        //装载思考能力
        streamThinkAbility = new StreamThinkAbility(
                executor,
                environmentInfo,
                inputContainer,
                memoryAbility,
                environmentInfo.getStreamingChatLanguageModel(),
                streamListeners);
    }

    public void start() {
        if (isRunning()) {
            return;
        }
        isRunning = true;
        audioOutputAbility.start();
        blblMsgInputAbility.start();
        streamThinkAbility.start();
    }


    public void stop() {
        if (!isRunning()) {
            return;
        }
        isRunning = false;
        audioOutputAbility.stop();
        blblMsgInputAbility.stop();
        streamThinkAbility.stop();
    }

    public boolean isRunning() {
        return isRunning;
    }


}
