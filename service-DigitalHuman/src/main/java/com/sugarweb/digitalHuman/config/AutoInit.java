package com.sugarweb.digitalHuman.config;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.*;
import com.sugarweb.digitalHuman.application.dto.DocDetailDto;
import com.sugarweb.digitalHuman.application.dto.DocSaveDto;
import com.sugarweb.digitalHuman.application.dto.KbDetailDto;
import com.sugarweb.digitalHuman.application.dto.KbSaveDto;
import com.sugarweb.digitalHuman.constans.DocSourceType;
import com.sugarweb.digitalHuman.domain.AgentInfo;
import com.sugarweb.digitalHuman.domain.DocInfo;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.digitalHuman.domain.StageInfo;
import com.sugarweb.digitalHuman.infra.llm.ModelPlatform;
import com.sugarweb.digitalHuman.infra.llm.ModelType;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.oss.application.dto.FileDetailDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Component
public class AutoInit implements ApplicationRunner {

    private final KbService kbService;
    private final AgentService agentService;
    private final SceneService sceneService;
    private final DocService docService;
    private final FileService fileService;
    private final ModelService modelService;

    public AutoInit(KbService kbService, AgentService agentService, SceneService sceneService, DocService docService, FileService fileService, ModelService modelService) {
        this.kbService = kbService;
        this.agentService = agentService;
        this.sceneService = sceneService;
        this.docService = docService;
        this.fileService = fileService;
        this.modelService = modelService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        defaultChatModelInfo();

        defaultEmbeddingModelInfo();

        defaultKbInfo();

        defaultAgentInfo();

    }

    private final String defaultChatModelId = "1";
    private final String defaultEmbeddingModelId = "1";
    private final String defaultSystemPromptId = "1";
    private final String defaultKbId = "1";
    private final String defaultStageId = "1";
    private final String defaultAgentId = "1";

    public ModelInfo defaultChatModelInfo() {
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setModelId(defaultChatModelId);
        modelInfo.setModelType(ModelType.CHAT.getValue());
        modelInfo.setModelPlatform(ModelPlatform.OLLAMA.getValue());
        modelInfo.setModelName("qwen2.5:3b");
        modelInfo.setBaseUrl("http://localhost:11434");
        Db.saveOrUpdate(modelInfo);
        return modelInfo;
    }

    public ModelInfo defaultEmbeddingModelInfo() {
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setModelId(defaultEmbeddingModelId);
        modelInfo.setModelType(ModelType.EMBEDDING.getValue());
        modelInfo.setModelPlatform(ModelPlatform.OLLAMA.getValue());
        modelInfo.setModelName("nomic-embed-text");
        modelInfo.setBaseUrl("http://localhost:11434");
        Db.saveOrUpdate(modelInfo);
        return modelInfo;
    }

    public KbDetailDto defaultKbInfo() {
        KbSaveDto kbSaveDto = new KbSaveDto();
        kbSaveDto.setKbName("test");
        kbSaveDto.setEmbeddingModelId(defaultEmbeddingModelId);
        kbSaveDto.setDescription("这是一个测试知识库");
        KbDetailDto save = kbService.save(kbSaveDto);
        return save;
    }

    public DocDetailDto step4(KbDetailDto kbDetailDto) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("D:\\test.txt");
        FileDetailDto docFile = fileService.upload("doc_file", fileInputStream, "text/plain", "test.txt");

        DocInfo docInfo = new DocInfo();
        docInfo.setDocName(docFile.getFilename());
        docInfo.setKbId(kbDetailDto.getKbId());
        docInfo.setSourceType(DocSourceType.FILE_UPLOAD.getValue());

        DocSaveDto saveDto = new DocSaveDto();
        saveDto.setDocName(docInfo.getDocName());
        saveDto.setKbId(docInfo.getKbId());
        saveDto.setSourceType(docInfo.getSourceType());
        DocDetailDto save = docService.save(saveDto);
        docService.parseStart(save.getKbId(), Collections.singletonList(save.getDocId()));
        return save;
    }

    public AgentInfo defaultAgentInfo() {
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentId("1");
        agentInfo.setAgentName("炫妹");
        agentInfo.setSystemPromptId(defaultSystemPromptId);
        agentInfo.setChatModelId(defaultChatModelId);
        agentInfo.setKbId(defaultKbId);
        agentInfo.setCreateTime(LocalDateTime.now());
        agentInfo.setUpdateTime(LocalDateTime.now());
        Db.saveOrUpdate(agentInfo);
        return agentInfo;
    }


    public StageInfo defaultStage() {
        StageInfo stageInfo = new StageInfo();
        stageInfo.setStageId(defaultStageId);
        stageInfo.setStageName("哔哩哔哩直播");
        stageInfo.setDescription("哔哩哔哩直播");
        stageInfo.setCreateTime(LocalDateTime.now());
        stageInfo.setUpdateTime(LocalDateTime.now());
        Db.saveOrUpdate(stageInfo);
        return stageInfo;
    }

}
