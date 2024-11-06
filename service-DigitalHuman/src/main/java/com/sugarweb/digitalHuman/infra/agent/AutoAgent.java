package com.sugarweb.digitalHuman.infra.agent;

import com.sugarweb.digitalHuman.domain.AgentInfo;
import com.sugarweb.digitalHuman.domain.KbInfo;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.digitalHuman.infra.MilvusEmbeddingStoreFactory;
import com.sugarweb.digitalHuman.infra.agent.component.input.InputContainer;
import com.sugarweb.digitalHuman.infra.agent.component.input.blbl.BlblMsgInputComponent;
import com.sugarweb.digitalHuman.infra.agent.component.memory.MemoryComponent;
import com.sugarweb.digitalHuman.infra.agent.component.memory.MemoryOutputListener;
import com.sugarweb.digitalHuman.infra.agent.component.output.audio.AudioOutputComponent;
import com.sugarweb.digitalHuman.infra.agent.component.output.audio.AudioOutputContainer;
import com.sugarweb.digitalHuman.infra.agent.component.output.audio.AudioOutputListener;
import com.sugarweb.digitalHuman.infra.agent.component.think.StreamListener;
import com.sugarweb.digitalHuman.infra.agent.component.think.StreamThinkComponent;
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
    private final ExecutorService executor;
    private final EnvironmentContext environmentContext;
    private boolean isRunning = false;
    //装载的能力
    private final StreamThinkComponent streamThinkComponent;
    private final AudioOutputComponent audioOutputComponent;
    private final MemoryComponent memoryComponent;
    private final BlblMsgInputComponent blblMsgInputComponent;

    public AutoAgent(ExecutorService executor, EnvironmentContext environmentContext) {
        this.executor = executor;
        this.environmentContext = environmentContext;

        AgentInfo agentInfo = environmentContext.getAgentInfo();
        KbInfo kbInfo = agentInfo.getKbInfo();
        if (kbInfo != null) {
            ModelInfo embeddingModelInfo = kbInfo.getEmbeddingModelInfo();
            EmbeddingModel embeddingModel = ModelFactory.creatEmbeddingModel(embeddingModelInfo);
            MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(kbInfo);
            //装载记忆能力
            memoryComponent = new MemoryComponent(embeddingModel, milvusEmbeddingStore, environmentContext.getPerformanceInfo().getPerformanceId());
        } else {
            memoryComponent = new MemoryComponent(null, null, environmentContext.getPerformanceInfo().getPerformanceId());
        }

        //创建记忆输出监听器
        StreamListener memoryOutputListener = new MemoryOutputListener(memoryComponent);
        //创建输入适配器
        InputContainer inputContainer = new InputContainer();

        AudioOutputContainer audioOutputContainer = new AudioOutputContainer();
        //装载输出能力
        audioOutputComponent = new AudioOutputComponent(executor, audioOutputContainer);
        //创建输出监听器
        StreamListener audioOutputListener = new AudioOutputListener(audioOutputContainer);
        //装载输入能力
        blblMsgInputComponent = new BlblMsgInputComponent(inputContainer);


        List<StreamListener> streamListeners = List.of(memoryOutputListener, audioOutputListener);
        //装载思考能力
        streamThinkComponent = new StreamThinkComponent(
                environmentContext,
                inputContainer,
                memoryComponent,
                streamListeners);
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
