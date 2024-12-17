package com.sugarweb.digitalHuman.infra.llm;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.common.ChatRole;
import com.sugarweb.digitalHuman.domain.*;
import com.sugarweb.digitalHuman.infra.PromptUtil;
import com.sugarweb.digitalHuman.infra.llm.input.InputContainer;
import com.sugarweb.digitalHuman.infra.llm.input.InputContent;
import com.sugarweb.digitalHuman.infra.llm.input.blbl.BlblInputComponent;
import com.sugarweb.digitalHuman.infra.llm.input.websocket.PerformanceWebsocketServer;
import com.sugarweb.digitalHuman.infra.llm.memory.DatasetMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.memory.PerformanceMemoryComponent;
import com.sugarweb.digitalHuman.infra.llm.output.OutputConsumer;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContainer;
import com.sugarweb.digitalHuman.infra.llm.output.websocket.WebsocketOutputConsumer;
import com.sugarweb.digitalHuman.infra.llm.thought.*;
import com.sugarweb.framework.common.Flag;
import com.sugarweb.framework.utils.BeanUtil;
import com.sugarweb.framework.utils.JsonUtil;
import com.sugarweb.framework.utils.TreeNode;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 自动舞台
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class AutoStage {

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final StageContext stageContext;
    //输入容器
    private final InputContainer inputContainer;
    //输出容器
    private final OutputContainer outputContainer;

    //记忆能力
    private final PerformanceMemoryComponent performanceMemoryComponent;
    private final DatasetMemoryComponent datasetMemoryComponent;

    private final StreamThoughtComponent streamThoughtComponent;
    private final BlblInputComponent blblInputComponent;
    private Future<?> stageThread = null;

    public AutoStage(StageContext stageContext) {
        this.stageContext = stageContext;
        Dataset dataset = stageContext.getDataset();
        if (dataset == null) {
            datasetMemoryComponent = null;
        } else {
            //装载记忆能力
            datasetMemoryComponent = new DatasetMemoryComponent(dataset);
        }
        performanceMemoryComponent = new PerformanceMemoryComponent();
        Stage stage = stageContext.getStage();

        List<OutputConsumer> outputConsumers = new ArrayList<>();

        //创建输出容器
        outputContainer = new OutputContainer(outputConsumers);

        //创建输入容器
        inputContainer = new InputContainer();
        if ("blbl".equals(stage.getLivePlatform())) {
            blblInputComponent = new BlblInputComponent(inputContainer);
        } else {
            blblInputComponent = null;
        }
        if (Flag.TRUE.equals(stage.getWebsocketMode())) {
            //准备websocket输出监听器
            WebsocketOutputConsumer websocketOutputConsumer = new WebsocketOutputConsumer(PerformanceWebsocketServer.getSessionMap());
            outputConsumers.add(websocketOutputConsumer);
            PerformanceWebsocketServer.loadInputContainer(stage.getStageId(), inputContainer);
        }

        List<StreamThoughtListener> streamThoughtListeners = new ArrayList<>();
        //创建输出监听器
        if (Flag.TRUE.equals(stage.getTtsMode())) {
            //创建tts输出监听器
            StreamThoughtListener ttsThoughtListener = new TtsOutputListener(outputContainer);
            streamThoughtListeners.add(ttsThoughtListener);
        } else {
            //创建默认输出监听器
            StreamThoughtListener defaultOutputListener = new DefaultOutputListener(outputContainer);
            streamThoughtListeners.add(defaultOutputListener);
        }
        //装载思考能力
        streamThoughtComponent = StreamThoughtComponent.builder()
                .modelId(stageContext.getActor().getChatModelId())
                .listeners(streamThoughtListeners)
                .build();
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
        if (blblInputComponent != null) {
            blblInputComponent.start();
        }
        stageThread = executor.submit(() -> {
            SpeedLimiter speedLimiter = new SpeedLimiter(0);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 限制最快1000ms 思考一次
                    speedLimiter.limit(1000);
                    long thinkId = System.currentTimeMillis();
                    Script script = stageContext.getScript();
                    List<ScriptNode> scriptNodeList = script.getScriptNodeList();
                    List<TreeNode<ScriptNode, String>> nodeList = TreeNode.build(scriptNodeList, ScriptNode::getNodeId, ScriptNode::getNodePid, (a, b) -> CompareUtil.compare(a.getNodeIndex(), b.getNodeIndex()));
                    for (TreeNode<ScriptNode, String> treeNode : nodeList) {
                        // 限制最快1000ms 思考一次
                        speedLimiter.limit(1000);
                        ScriptNode scriptNode = treeNode.getData();
                        if (shouldAnswer()) {
                            answer(thinkId);
                        } else {
                            script(thinkId, script, scriptNode);
                        }
                    }
                    //结束后重播
                } catch (Exception e) {
                    log.error("ai思考异常 error:{}", e.getMessage(), e);
                }
            }
        });
    }


    public void script(long thinkId, Script script, ScriptNode scriptNode) {
        log.info("开始执行脚本，thinkId:{}, scriptNode:{}", thinkId, scriptNode);
        String promptTemplate = script.getPromptTemplate();
        HashMap<String, Object> contextVariables = new HashMap<>();
        String systemPrompt = PromptUtil.getPrompt(promptTemplate, contextVariables);

        ThoughtRequest thoughtRequest = new ThoughtRequest();
        thoughtRequest.setThoughtId(thinkId);
        thoughtRequest.setSystemMsg(systemPrompt);
        List<RoleMsg> roleMsgList = new ArrayList<>();
        thoughtRequest.setRoleMsgList(roleMsgList);
        thoughtRequest.put("scriptNode", scriptNode);

        Stage stage = stageContext.getStage();
        Actor actor = stageContext.getActor();
        StagePerformance stagePerformance = stageContext.getStagePerformance();
        StagePerformanceMsg lastUserMsg = performanceMemoryComponent.loadMemory(stage.getPerformanceId(), null);

        //组装历史消息
        if (lastUserMsg != null) {
            roleMsgList.addAll(lastUserMsg.prepareHistoryMessage());
        }
        roleMsgList.add(new RoleMsg(ChatRole.USER.getValue(), scriptNode.getScriptContent()));

        //记录消息日志
        StagePerformanceMsg currentMsg = new StagePerformanceMsg();
        if (lastUserMsg != null) {
            currentMsg.setMsgPid(lastUserMsg.getMsgId());
        }
        currentMsg.setMessage(JsonUtil.toJsonStr(roleMsgList));
        currentMsg.setPerformanceId(stagePerformance.getPerformanceId());
        currentMsg.setStageId(stage.getStageId());
        currentMsg.setChatModelId(actor.getChatModelId());
        currentMsg.setActorId(actor.getActorId());
        currentMsg.setSystemMsg(systemPrompt);
        currentMsg.setQuestion(scriptNode.getScriptContent());
        currentMsg.setUserId(null);
        currentMsg.setMsgType("script");
        currentMsg.setStartTime(LocalDateTime.now());

        Future<String> answerFuture = streamThoughtComponent.streamThink(thoughtRequest);
        try {
            String answer = answerFuture.get();
            currentMsg.setAnswer(answer);
            currentMsg.setEndTime(LocalDateTime.now());
            //计算耗时毫秒
            currentMsg.setCostTime(Duration.between(currentMsg.getStartTime(), currentMsg.getEndTime()).toMillis());
        } catch (InterruptedException | ExecutionException e) {
            log.error("无法获取ai响应: {}", e.getCause(), e);
        }
        log.info("currentMsg: {}", currentMsg);
        //记录对话
        performanceMemoryComponent.memorize(currentMsg);
    }

    public boolean shouldAnswer() {
        //权重越高，回答概率越高
        int weight = getAnswerWeight();
        return new Random().nextInt(10) < weight;
    }

    public int getAnswerWeight() {
        //1.根据size判断,size 0-10条，w1 0-10, 最大10
        int size = inputContainer.size();
        int w1 = Math.min(size, 10);
        return w1;
    }

    public void answer(long thinkId) {
        log.info("开始执行回答，thinkId:{}", thinkId);
        // 从消息队列中获取消息
        InputContent inputContent = inputContainer.poll();
        if (inputContent == null) {
            return;
        }
        Stage stage = stageContext.getStage();
        StagePerformance stagePerformance = stageContext.getStagePerformance();

        ThoughtRequest thoughtRequest = new ThoughtRequest();
        //记录思考id
        thoughtRequest.setThoughtId(thinkId);

        //记录上下文变量
        thoughtRequest.put("userId", inputContent.getUserId());
        thoughtRequest.put("username", inputContent.getUsername());
        // 用户提问
        thoughtRequest.put("question", inputContent.getContent());

        //获取相关召回文档
        String documents = "无";
        if (datasetMemoryComponent != null) {
            String retrievalSegment = datasetMemoryComponent.getRetrievalSegment(inputContent.getContent());
            if (StrUtil.isNotEmpty(retrievalSegment)) {
                documents = retrievalSegment;
            }
        }
        thoughtRequest.put("documents", documents);

        // 系统提示语
        Actor actor = stageContext.getActor();
        String systemPrompt = PromptUtil.getPrompt(actor.getPromptTemplate(), thoughtRequest.getContextVariables());
        thoughtRequest.setSystemMsg(systemPrompt);
        // 准备提问消息
        StagePerformanceMsg lastUserMsg = performanceMemoryComponent.loadMemory(stagePerformance.getPerformanceId(), inputContent.getUserId());
        List<RoleMsg> roleMsgList = new ArrayList<>();
        if (lastUserMsg != null) {
            List<RoleMsg> historyMessage = lastUserMsg.prepareHistoryMessage();
            roleMsgList.addAll(historyMessage);
        }
        roleMsgList.add(new RoleMsg(ChatRole.USER.name(), inputContent.getContent()));
        thoughtRequest.setRoleMsgList(roleMsgList);

        //记录当前消息
        StagePerformanceMsg currentMsg = new StagePerformanceMsg();
        if (lastUserMsg != null) {
            currentMsg.setMsgPid(lastUserMsg.getMsgId());
        }
        currentMsg.setMessage(JsonUtil.toJsonStr(roleMsgList));
        currentMsg.setPerformanceId(stagePerformance.getPerformanceId());
        currentMsg.setStageId(stage.getStageId());
        currentMsg.setChatModelId(actor.getChatModelId());
        currentMsg.setActorId(actor.getActorId());
        currentMsg.setSystemMsg(systemPrompt);
        currentMsg.setQuestion(inputContent.getContent());

        currentMsg.setUserId(inputContent.getUserId());
        currentMsg.setMsgType("user");
        currentMsg.setStartTime(LocalDateTime.now());

        Future<String> answerFuture = streamThoughtComponent.streamThink(thoughtRequest);
        try {
            String answer = answerFuture.get();
            currentMsg.setAnswer(answer);
            currentMsg.setEndTime(LocalDateTime.now());
            currentMsg.setCostTime(Duration.between(currentMsg.getStartTime(), currentMsg.getEndTime()).toMillis());
        } catch (InterruptedException | ExecutionException e) {
            log.error("ai思考异常 error:{}", e.getMessage(), e);
        }
        log.info("currentMsg: {}", currentMsg);
        //记录对话
        performanceMemoryComponent.memorize(currentMsg);

    }

    public void stop() {
        if (!isRunning()) {
            return;
        }
        PerformanceWebsocketServer performanceWebsocketServer = BeanUtil.getBean(PerformanceWebsocketServer.class);
        performanceWebsocketServer.unloadInputContainer(stageContext.getStage().getStageId());
        if (blblInputComponent != null) {
            blblInputComponent.stop();
        }
        stageThread.cancel(true);
        StagePerformance stagePerformance = stageContext.getStagePerformance();
        stagePerformance.setEndTime(LocalDateTime.now());
        Db.updateById(stagePerformance);
    }

    public boolean isRunning() {
        return stageThread != null && !stageThread.isDone();
    }

}
