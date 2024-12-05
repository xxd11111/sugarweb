package com.sugarweb.digitalHuman;

import com.sugarweb.digitalHuman.domain.*;
import com.sugarweb.digitalHuman.infra.llm.ModelPlatform;
import com.sugarweb.digitalHuman.infra.llm.ModelType;
import com.sugarweb.digitalHuman.infra.llm.StageManager;
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
            // 模型设置
            Model chatModel = new Model();
            chatModel.setModelId("qwen2.5:7b");
            chatModel.setModelType(ModelType.CHAT.getValue());
            chatModel.setModelName("qwen2.5:7b");
            chatModel.setModelPlatform(ModelPlatform.OLLAMA.getValue());
            chatModel.setBaseUrl("http://localhost:11434");

            // tts模型设置
            Model ttsModel = new Model();
            ttsModel.setModelId("chatTts");
            ttsModel.setModelType(ModelType.TTS.getValue());
            ttsModel.setBaseUrl("http://127.0.0.1:9966/tts");

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
            actor.setTtsModelId(ttsModel.getModelId());
            actor.setTtsModelConfig("");
            actor.setChatModelId(chatModel.getModelId());
            actor.setChatModeConfig("");
            // 暂不使用数据集
            // actor.setDatasetId("default");

            //脚本设置
            Script script = new Script();
            script.setScriptId("default");
            script.setScriptName("默认脚本");
            script.setDescription("默认脚本");
            script.setPromptTemplate("""
                    讲三国演义
                    """);
            int index = 1;
            ArrayList<ScriptNode> scriptNodeList = new ArrayList<>();
            scriptNodeList.add(createScriptNode(index++, "你好，我是小明，请问你有什么问题吗？"));
            scriptNodeList.add(createScriptNode(index++, "你好，我是小明，请问你有什么问题吗？"));
            scriptNodeList.add(createScriptNode(index++, "你好，我是小明，请问你有什么问题吗？"));
            scriptNodeList.add(createScriptNode(index++, "你好，我是小明，请问你有什么问题吗？"));
            scriptNodeList.add(createScriptNode(index++, "你好，我是小明，请问你有什么问题吗？"));
            script.setScriptNodeList(scriptNodeList);

            Stage stage = new Stage();
            stage.setStageId("default");
            stage.setStageName("哔哩哔哩直播");
            stage.setDescription("哔哩哔哩直播测试");
            stage.setActorId(actor.getActorId());
            stage.setScriptId(script.getScriptId());
            stage.setTtsMode(Flag.FALSE);
            stage.setStatus(Flag.FALSE);
            stage.setLivePlatform("blbl");
            stage.setLocalOutputMode(Flag.TRUE);
            stage.setWebsocketMode(Flag.TRUE);

            stageManager.startStage(stage);
        };
    }

    private ScriptNode createScriptNode(int index, String content) {
        ScriptNode scriptNode = new ScriptNode();
        scriptNode.setNodeId("node" + index);
        scriptNode.setNodePid(null);
        scriptNode.setNodeIndex(String.valueOf(index));
        scriptNode.setScriptContent(content);
        return scriptNode;
    }

}
