package com.sugarweb.digitalHuman.infra.llm;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.domain.*;
import com.sugarweb.digitalHuman.infra.PromptUtil;
import com.sugarweb.digitalHuman.infra.llm.input.InputContainer;
import com.sugarweb.digitalHuman.infra.llm.input.InputContent;
import com.sugarweb.digitalHuman.infra.llm.input.blbl.BlblInputComponent;
import com.sugarweb.digitalHuman.infra.llm.memory.DatasetMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.memory.MemoryStreamThoughtListener;
import com.sugarweb.digitalHuman.infra.llm.memory.PerformanceMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContainer;
import com.sugarweb.digitalHuman.infra.llm.thought.StreamThoughtComponent;
import com.sugarweb.digitalHuman.infra.llm.thought.StreamThoughtListener;
import com.sugarweb.digitalHuman.infra.llm.thought.ThoughtContext;
import com.sugarweb.digitalHuman.infra.llm.thought.TtsThoughtListener;
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
    private final OutputContainer outputContainer;

    private final PerformanceMemoryComponent performanceMemoryComponent;
    private final StreamThoughtComponent streamThoughtComponent;
    private final DatasetMemoryComponent datasetMemoryComponent;
    private final BlblInputComponent blblInputComponent;
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
        StreamThoughtListener memoryOutputListener = new MemoryStreamThoughtListener(performanceMemoryComponent);
        //创建输入容器
        inputContainer = new InputContainer();
        //创建输出容器
        outputContainer = new OutputContainer();
        //todo tts设置为可选功能
        //创建输出监听器
        StreamThoughtListener ttsThoughtListener = new TtsThoughtListener(outputContainer);
        //装载输入能力
        blblInputComponent = new BlblInputComponent(inputContainer);
        //创建流式思考监听者
        List<StreamThoughtListener> streamThoughtListeners = List.of(memoryOutputListener, ttsThoughtListener);
        //装载思考能力
        streamThoughtComponent = StreamThoughtComponent.builder()
                .stageContext(stageContext)
                .listeners(streamThoughtListeners)
                .build();
        //todo 初始化输出能力
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

    public void start() {
        if (isRunning()) {
            return;
        }
        blblInputComponent.start();
        stageThread = executor.submit(() -> {
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
        Script script = stageContext.getScript();
        List<ScriptNode> scriptNodeList = script.getScriptNodeList();
        //todo
    }

    public boolean shouldAnswer() {
        //todo
        return true;
    }

    public void answer(long thinkId) {
        // 从消息队列中获取消息
        InputContent inputContent = inputContainer.poll();
        if (inputContent == null) {
            return;
        }
        StagePerformance stagePerformance = stageContext.getStagePerformance();

        ThoughtContext thoughtContext = new ThoughtContext();
        //记录思考id
        thoughtContext.setThoughtId(thinkId);

        StagePerformanceMsg currentMsg = new StagePerformanceMsg();
        currentMsg.setStartTime(LocalDateTime.now());
        currentMsg.setUserId(inputContent.getUserId());
        currentMsg.setQuestion(inputContent.getContent());
        currentMsg.setMsgType("user");
        currentMsg.setMsgId(thinkId + "");
        currentMsg.setPerformanceId(stagePerformance.getPerformanceId());
        //记录当前消息
        thoughtContext.setCurrentMsg(currentMsg);

        //记录上下文变量
        thoughtContext.put("userId", inputContent.getUserId());
        thoughtContext.put("username", inputContent.getUsername());
        // 用户提问
        thoughtContext.put("question", inputContent.getContent());
        thoughtContext.setQuestionMsg(inputContent.getContent());

        //获取相关召回文档
        String documents = "无";
        if (datasetMemoryComponent != null) {
            String retrievalSegment = datasetMemoryComponent.getRetrievalSegment(inputContent.getContent());
            if (StrUtil.isNotEmpty(retrievalSegment)) {
                documents = retrievalSegment;
            }
        }
        thoughtContext.put("documents", documents);

        // 系统提示语
        Actor actor = stageContext.getActor();
        String systemPrompt = PromptUtil.getPrompt(actor.getPromptTemplate(), PromptUtil.parsePromptVariables(actor.getPromptTemplate()), thoughtContext.getContextVariables());
        thoughtContext.setSystemMsg(systemPrompt);
        // 历史消息
        StagePerformanceMsg lastUserMsg = performanceMemoryComponent.loadMemory(stagePerformance.getPerformanceId(), inputContent.getUserId());
        thoughtContext.setHistoryMsg(lastUserMsg);
        streamThoughtComponent.streamThink(thoughtContext);
    }

    public void stop() {
        if (!isRunning()) {
            return;
        }
        blblInputComponent.stop();
        stageThread.cancel(true);
        StagePerformance stagePerformance = stageContext.getStagePerformance();
        stagePerformance.setEndTime(LocalDateTime.now());
        Db.updateById(stagePerformance);
    }

    public boolean isRunning() {
        return stageThread != null && !stageThread.isDone();
    }

}
