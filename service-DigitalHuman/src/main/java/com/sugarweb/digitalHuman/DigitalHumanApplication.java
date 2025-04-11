package com.sugarweb.digitalHuman;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.entity.*;
import com.sugarweb.digitalHuman.component.llm.ModelPlatform;
import com.sugarweb.digitalHuman.component.llm.ModelType;
import com.sugarweb.digitalHuman.component.llm.StageManager;
import com.sugarweb.framework.common.Flag;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.ArrayList;

/**
 * 聊天助手应用
 *
 * @author xxd
 * @version 1.0
 */
@SpringBootApplication
@MapperScan({"com.sugarweb.**.entity.mapper", "com.sugarweb.**.infra.mapper", "com.sugarweb.digitalHuman.infra.mapper"})
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
            // 模型设置
            Model chatModel = new Model();
            chatModel.setModelId("qwen2:1.5b");
            chatModel.setModelType(ModelType.CHAT.getValue());
            chatModel.setModelName("qwen2:1.5b");
            chatModel.setModelPlatform(ModelPlatform.OLLAMA.getValue());
            // chatModel.setBaseUrl("http://192.168.193.151:11434");
            chatModel.setBaseUrl("http://127.0.0.1:11434");
            Db.saveOrUpdate(chatModel);

            // tts模型设置
            Model ttsModel = new Model();
            ttsModel.setModelId("chatTts");
            ttsModel.setModelType(ModelType.TTS.getValue());
            ttsModel.setBaseUrl("http://127.0.0.1:9966/tts");
            Db.saveOrUpdate(ttsModel);

            // 演员设置
            Agent agent = new Agent();
            agent.setAgentId("default");
            agent.setAgentName("炫妹");
            agent.setPrompt("""
                    你现在是友好的人类【炫妹】，接下来请根据事件消息做出回答；
                    要求：你的每句回答都会以语音的形式直接展现给观众，结果以口语化的形式表达，不能用书面语言，返回结果只能是中文。
                    
                    以下是参考文档：
                    {{documents}}
                    """);
            agent.setTtsModelId(ttsModel.getModelId());
            agent.setTtsModelConfig("");
            agent.setChatModelId(chatModel.getModelId());
            agent.setChatModeConfig("");
            Db.saveOrUpdate(agent);
            // 暂不使用数据集
            // actor.setDatasetId("default");

            //脚本设置
            Script script = new Script();
            script.setScriptId("default");
            script.setScriptName("默认脚本");
            script.setDescription("默认脚本");
            script.setPromptTemplate("""
                    你现在是个主播，根据提问来回答，每次回答限制500字以内。
                    """);
            Db.saveOrUpdate(script);
            int index = 1;
            ArrayList<ScriptNode> scriptNodeList = new ArrayList<>();
            scriptNodeList.add(createScriptNode(index++, "讲3个笑话"));
            scriptNodeList.add(createScriptNode(index++, "讲下三国演义的故事"));
            scriptNodeList.add(createScriptNode(index++, "讲下水浒传的故事"));
            scriptNodeList.add(createScriptNode(index++, "讲下魔兽争霸的故事"));
            scriptNodeList.add(createScriptNode(index++, "讲下星际争霸的故事"));
            Db.saveOrUpdateBatch(scriptNodeList);

            Stage stage = new Stage();
            stage.setStageId("default");
            stage.setStageName("本地测试");
            stage.setDescription("本地测试");
            stage.setActorId(agent.getAgentId());
            stage.setScriptId(script.getScriptId());
            stage.setTtsMode(Flag.FALSE);
            stage.setStatus(Flag.FALSE);
            // stage.setLivePlatform("blbl");
            stage.setOutputMode(Flag.TRUE);
            stage.setWebsocketMode(Flag.TRUE);
            Db.saveOrUpdate(stage);

            stageManager.startStage(stage);
        };
    }

    private ScriptNode createScriptNode(int index, String content) {
        ScriptNode scriptNode = new ScriptNode();
        scriptNode.setScriptId("default");
        scriptNode.setNodeId("node" + index);
        scriptNode.setNodePid(null);
        scriptNode.setNodeIndex(String.valueOf(index));
        scriptNode.setScriptContent(content);
        return scriptNode;
    }

}
