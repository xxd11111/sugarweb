package com.sugarweb.digitalHuman.infra.agent;

import com.sugarweb.digitalHuman.domain.AgentInfo;
import com.sugarweb.digitalHuman.domain.KbInfo;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.digitalHuman.infra.MilvusEmbeddingStoreFactory;
import com.sugarweb.digitalHuman.infra.agent.input.InputContainer;
import com.sugarweb.digitalHuman.infra.agent.input.blbl.BlblMsgInputComponent;
import com.sugarweb.digitalHuman.infra.agent.memory.ChatMemoryComponent;
import com.sugarweb.digitalHuman.infra.agent.memory.KbMemoryComponent;
import com.sugarweb.digitalHuman.infra.agent.memory.ChatMemoryStreamListener;
import com.sugarweb.digitalHuman.infra.agent.output.audio.AudioOutputComponent;
import com.sugarweb.digitalHuman.infra.agent.output.audio.AudioOutputContainer;
import com.sugarweb.digitalHuman.infra.agent.output.audio.AudioOutputListener;
import com.sugarweb.digitalHuman.infra.agent.think.StreamListener;
import com.sugarweb.digitalHuman.infra.agent.think.StreamThinkComponent;
import com.sugarweb.digitalHuman.infra.llm.ModelFactory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
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
    private boolean isRunning = false;
    //装载的能力
    private final StreamThinkComponent streamThinkComponent;
    private final AudioOutputComponent audioOutputComponent;
    private KbMemoryComponent kbMemoryComponent = null;
    private final BlblMsgInputComponent blblMsgInputComponent;

    public AutoAgent(ExecutorService executor, EnvironmentContext environmentContext) {
        AgentInfo agentInfo = environmentContext.getAgentInfo();
        KbInfo kbInfo = agentInfo.getKbInfo();
        if (kbInfo != null) {
            //装载记忆能力
             kbMemoryComponent = new KbMemoryComponent(kbInfo);
        }
        ChatMemoryComponent chatMemoryComponent = new ChatMemoryComponent(environmentContext.getPerformanceInfo().getPerformanceId());

        //创建记忆输出监听器
        StreamListener memoryOutputListener = new ChatMemoryStreamListener(chatMemoryComponent);
        //创建输入容器
        InputContainer inputContainer = new InputContainer();

        AudioOutputContainer audioOutputContainer = new AudioOutputContainer();
        //装载输出组件
        audioOutputComponent = new AudioOutputComponent(executor, audioOutputContainer);
        //创建输出监听器
        StreamListener audioOutputListener = new AudioOutputListener(audioOutputContainer);
        //装载输入能力
        blblMsgInputComponent = new BlblMsgInputComponent(inputContainer);

        List<StreamListener> streamListeners = List.of(memoryOutputListener, audioOutputListener);
        //装载思考能力
        streamThinkComponent = StreamThinkComponent.builder()
                .environmentContext(environmentContext)
                .listeners(streamListeners)
                .inputContainer(inputContainer)
                .kbMemoryComponent(kbMemoryComponent)
                .chatMemoryComponent(chatMemoryComponent)
                .build();
    }

    public void start() {
        if (isRunning()) {
            return;
        }
        isRunning = true;
        audioOutputComponent.start();
        blblMsgInputComponent.start();
        streamThinkComponent.start();
    }


    public void stop() {
        if (!isRunning()) {
            return;
        }
        isRunning = false;
        audioOutputComponent.stop();
        blblMsgInputComponent.stop();
        streamThinkComponent.stop();
    }

    public boolean isRunning() {
        return isRunning;
    }


}
