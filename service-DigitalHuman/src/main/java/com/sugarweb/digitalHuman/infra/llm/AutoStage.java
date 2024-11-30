package com.sugarweb.digitalHuman.infra.llm;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.digitalHuman.domain.*;
import com.sugarweb.digitalHuman.infra.PromptUtil;
import com.sugarweb.digitalHuman.infra.llm.input.InputContainer;
import com.sugarweb.digitalHuman.infra.llm.input.blbl.BlblMsgInputComponent;
import com.sugarweb.digitalHuman.infra.llm.input.blbl.BlblMsgPrompt;
import com.sugarweb.digitalHuman.infra.llm.memory.ChatMemoryStreamListener;
import com.sugarweb.digitalHuman.infra.llm.memory.DatasetMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.memory.PerformanceMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.output.audio.AudioOutputComponent;
import com.sugarweb.digitalHuman.infra.llm.output.audio.AudioOutputContainer;
import com.sugarweb.digitalHuman.infra.llm.output.audio.AudioOutputListener;
import com.sugarweb.digitalHuman.infra.llm.thought.StreamListener;
import com.sugarweb.digitalHuman.infra.llm.thought.StreamThoughtComponent;
import com.sugarweb.digitalHuman.infra.llm.thought.ThoughtContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 自动舞台
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class AutoStage {

    private final ExecutorService executor;
    private final StageContext stageContext;
    //装载的能力
    private final InputContainer inputContainer;
    private final AudioOutputContainer audioOutputContainer;

    private final PerformanceMemoryComponent performanceMemoryComponent;
    private final StreamThoughtComponent streamThoughtComponent;
    private final AudioOutputComponent audioOutputComponent;
    private final DatasetMemoryComponent datasetMemoryComponent;
    private final BlblMsgInputComponent blblMsgInputComponent;
    private Future<?> stageThread = null;

    public AutoStage(ExecutorService executor, StageContext stageContext) {
        this.executor = executor;
        this.stageContext = stageContext;
        Dataset dataset = stageContext.getDataset();
        if (dataset == null) {
            datasetMemoryComponent = null;
        } else {
            //装载记忆能力
            datasetMemoryComponent = new DatasetMemoryComponent(dataset);
        }
        performanceMemoryComponent = new PerformanceMemoryComponent();

        //创建记忆输出监听器
        StreamListener memoryOutputListener = new ChatMemoryStreamListener(performanceMemoryComponent);
        //创建输入容器
        inputContainer = new InputContainer();
        //创建输出容器
        audioOutputContainer = new AudioOutputContainer();
        //装载输出组件
        audioOutputComponent = new AudioOutputComponent(executor, audioOutputContainer);
        //创建输出监听器
        StreamListener audioOutputListener = new AudioOutputListener(audioOutputContainer);
        //装载输入能力
        blblMsgInputComponent = new BlblMsgInputComponent(inputContainer);
        //创建流式思考监听者
        List<StreamListener> streamListeners = List.of(memoryOutputListener, audioOutputListener);
        //装载思考能力
        streamThoughtComponent = StreamThoughtComponent.builder()
                .stageContext(stageContext)
                .listeners(streamListeners)
                .build();
    }

    public void start() {
        if (isRunning()) {
            return;
        }
        blblMsgInputComponent.start();
        audioOutputComponent.start();
        stageThread = executor.submit(() -> {
            StreamThoughtComponent.SpeedLimiter speedLimiter = new StreamThoughtComponent.SpeedLimiter(0);
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
        Script script = stageContext.getScript();
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
        String systemPrompt = PromptUtil.getPrompt(stageContext.getActor().getPromptTemplate(), stageContext.getActor().getPromptVariables(), thoughtContext.getContextVariables());
        thoughtContext.setSystemMsg(systemPrompt);

        // 历史消息
        StagePerformanceMsg lastUserMsg = performanceMemoryComponent.lastUserMsg(performanceMsg.getPerformanceId(), blblUser.getBlblUid());
        thoughtContext.setHistoryMsg(lastUserMsg);

        streamThoughtComponent.streamThink(thoughtContext);
    }


    public void stop() {
        if (!isRunning()) {
            return;
        }
        blblMsgInputComponent.stop();
        audioOutputComponent.stop();
        stageThread.cancel(true);
    }

    public boolean isRunning() {
        return stageThread != null && !stageThread.isDone();
    }

}
