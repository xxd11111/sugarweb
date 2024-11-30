package com.sugarweb.digitalHuman;

import com.sugarweb.digitalHuman.application.StageService;
import com.sugarweb.digitalHuman.domain.*;
import com.sugarweb.digitalHuman.infra.llm.ModelPlatform;
import com.sugarweb.digitalHuman.infra.llm.ModelType;
import com.sugarweb.digitalHuman.infra.llm.StageManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 聊天助手应用
 *
 * @author xxd
 * @version 1.0
 */
@SpringBootApplication
@MapperScan({"com.sugarweb.**.domain.mapper", "com.sugarweb.**.infra.mapper", "com.sugarweb.digitalHuman.infra.mapper"})
@EnableWebSocket
public class DigitalHumanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalHumanApplication.class, args);
    }

    /**
     * 启动一个默认的stage
     */
    @Bean
    public ApplicationRunner autoActor(StageManager stageManager) {
        return args -> {
            Stage stage = new Stage();
            stage.setStageId("default");
            stage.setStageName("哔哩哔哩直播");
            stage.setDescription("哔哩哔哩直播测试");

            // 演员设置
            Actor actor = new Actor();
            actor.setActorId("default");
            actor.setActorName("小明");
            actor.setPromptTemplate("""
                    你现在是友好的人类【炫妹】，接下来请根据事件消息做出回答；
                    要求：你的每句回答都会以语音的形式直接展现给观众，结果以口语化的形式表达，不能用书面语言，返回结果只能是中文。
                    
                    以下是参考文档：
                    {{documents}}
                    """);
            actor.setPromptVariables(new String[]{"documents"});

            Model ttsModel = new Model();
            ttsModel.setModelId("chatTts");
            ttsModel.setModelType(ModelType.TTS.getValue());

            actor.setTtsModelId(ttsModel.getModelId());
            actor.setTtsModelConfig("");

            Model chatModel = new Model();
            chatModel.setModelId("qwen2.5:7b");
            chatModel.setModelType(ModelType.CHAT.getValue());
            chatModel.setModelName("qwen2.5:7b");
            chatModel.setModelPlatform(ModelPlatform.OLLAMA.getValue());
            chatModel.setBaseUrl("http://localhost:11434");
            actor.setChatModelId(chatModel.getModelId());
            actor.setChatModeConfig("");
            // actor.setDatasetId("default");

            stage.setActorId(actor.getActorId());

            // //脚本设置
            // Script script = new Script();
            // script.setScriptId("default");
            // script.setScriptName("默认脚本");
            // script.setDescription("默认脚本");
            // script.setPromptTemplate("");
            // script.setPromptVariables(new String[]{});
            // ArrayList<ScriptNode> scriptNodeList = new ArrayList<>();
            // for (int i = 0; i < 5; i++) {
            //     ScriptNode scriptNode = new ScriptNode();
            //     scriptNode.setNodeId("node" + i);
            //     scriptNode.setNodePid(null);
            //     scriptNode.setNodeName("节点" + i);
            //     scriptNode.setNodeIndex(String.valueOf(i));
            //     scriptNode.setDescription("节点" + i);
            //     scriptNodeList.add(scriptNode);
            // }
            // script.setScriptNodeList(scriptNodeList);
            // stage.setScript(script);
            // stage.setScriptId(script.getScriptId());

            StagePerformance stagePerformance = new StagePerformance();
            stagePerformance.setPerformanceId("default");
            stagePerformance.setStageId(stage.getStageId());
            stagePerformance.setStageName(stage.getStageName());
            // stagePerformance.setScriptId(stage.getScriptId());
            // stagePerformance.setScriptName(stage.getScript().getScriptName());
            stagePerformance.setActorId(stage.getActorId());
            stagePerformance.setActorName(actor.getActorName());
            stagePerformance.setStartTime(LocalDateTime.now());
            stagePerformance.setTitle("默认标题");

            stage.setPerformanceId(stagePerformance.getPerformanceId());
            stageManager.startStage(stage);
        };
    }

}
