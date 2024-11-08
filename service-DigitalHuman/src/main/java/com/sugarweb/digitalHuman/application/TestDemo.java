package com.sugarweb.digitalHuman.application;

import com.sugarweb.digitalHuman.application.dto.*;
import com.sugarweb.digitalHuman.constants.DocSourceType;
import com.sugarweb.digitalHuman.domain.DocInfo;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.digitalHuman.infra.llm.ModelPlatform;
import com.sugarweb.digitalHuman.infra.llm.ModelType;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.oss.application.dto.FileDetailDto;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class TestDemo {

    private final ModelService modelService;
    private final KbService kbService;
    private final FileService fileService;
    private final DocService docService;
    private final AgentService agentService;

    public TestDemo(ModelService modelService, KbService kbService, FileService fileService, DocService docService, AgentService agentService) {
        this.modelService = modelService;
        this.kbService = kbService;
        this.fileService = fileService;
        this.docService = docService;
        this.agentService = agentService;
    }

    // step1 配置问答模型
    public ModelInfo step1() {
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setModelType(ModelType.CHAT.getValue());
        modelInfo.setModelPlatform(ModelPlatform.OLLAMA.getValue());
        modelInfo.setModelName("qwen2.5:3b");
        modelInfo.setBaseUrl("http://localhost:11434");
        modelService.save(modelInfo);
        return modelInfo;
    }

    // step2 配置向量模型
    public ModelInfo step2() {
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setModelType(ModelType.EMBEDDING.getValue());
        modelInfo.setModelPlatform(ModelPlatform.OLLAMA.getValue());
        modelInfo.setModelName("nomic-embed-text");
        modelInfo.setBaseUrl("http://localhost:11434");
        modelService.save(modelInfo);
        return modelInfo;
    }

    public KbDetailDto step3(ModelInfo modelInfo) {
        KbSaveDto kbSaveDto = new KbSaveDto();
        kbSaveDto.setKbName("test");
        kbSaveDto.setEmbeddingModelId(modelInfo.getModelId());
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

    public void step5(ModelInfo chatModelInfo) {
        AgentSaveDto saveDto = new AgentSaveDto();
        saveDto.setAgentName("test_agent");
        saveDto.setChatModelId(chatModelInfo.getModelId());
        PromptTemplateSaveDto systemPrompt = new PromptTemplateSaveDto();
        systemPrompt.setContent("你是一个AI，请回答我的问题");
        saveDto.setSystemPrompt(systemPrompt);
        agentService.save(saveDto);
    }


}
