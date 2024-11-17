package com.sugarweb.digitalHuman.infra.llm;

import com.sugarweb.digitalHuman.domain.Dataset;
import com.sugarweb.digitalHuman.infra.llm.input.InputContainer;
import com.sugarweb.digitalHuman.infra.llm.input.blbl.BlblMsgInputComponent;
import com.sugarweb.digitalHuman.infra.llm.memory.PerformanceMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.memory.ChatMemoryStreamListener;
import com.sugarweb.digitalHuman.infra.llm.memory.DatasetMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.output.audio.AudioOutputComponent;
import com.sugarweb.digitalHuman.infra.llm.output.audio.AudioOutputContainer;
import com.sugarweb.digitalHuman.infra.llm.output.audio.AudioOutputListener;
import com.sugarweb.digitalHuman.infra.llm.thought.StreamListener;
import com.sugarweb.digitalHuman.infra.llm.thought.StreamThoughtComponent;
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
public class AutoStage {
    private boolean isRunning = false;
    //装载的能力
    private final StreamThoughtComponent streamThoughtComponent;
    private final AudioOutputComponent audioOutputComponent;
    private final DatasetMemoryComponent datasetMemoryComponent;
    private final BlblMsgInputComponent blblMsgInputComponent;

    public AutoStage(ExecutorService executor, StageContext stageContext) {
        Dataset dataset = stageContext.getDataset();
        if (dataset == null) {
            datasetMemoryComponent = null;
        } else {
            //装载记忆能力
            datasetMemoryComponent = new DatasetMemoryComponent(dataset);
        }
        PerformanceMemoryComponent performanceMemoryComponent = new PerformanceMemoryComponent();

        //创建记忆输出监听器
        StreamListener memoryOutputListener = new ChatMemoryStreamListener(performanceMemoryComponent);
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
        streamThoughtComponent = StreamThoughtComponent.builder()
                .stageContext(stageContext)
                .listeners(streamListeners)
                .inputContainer(inputContainer)
                .datasetMemoryComponent(datasetMemoryComponent)
                .build();
    }

    public void start() {
        if (isRunning()) {
            return;
        }
        isRunning = true;
        audioOutputComponent.start();
        blblMsgInputComponent.start();
        streamThoughtComponent.start();
    }


    public void stop() {
        if (!isRunning()) {
            return;
        }
        isRunning = false;
        audioOutputComponent.stop();
        blblMsgInputComponent.stop();
        streamThoughtComponent.stop();
    }

    public boolean isRunning() {
        return isRunning;
    }


}
