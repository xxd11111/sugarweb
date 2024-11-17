package com.sugarweb.digitalHuman.config;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.*;
import com.sugarweb.digitalHuman.application.dto.DocumentDetailDto;
import com.sugarweb.digitalHuman.application.dto.DocumentSaveDto;
import com.sugarweb.digitalHuman.application.dto.DatasetDetailDto;
import com.sugarweb.digitalHuman.application.dto.DatasetSaveDto;
import com.sugarweb.digitalHuman.constants.DocSourceType;
import com.sugarweb.digitalHuman.domain.Actor;
import com.sugarweb.digitalHuman.domain.DatasetDocument;
import com.sugarweb.digitalHuman.domain.Model;
import com.sugarweb.digitalHuman.domain.Stage;
import com.sugarweb.digitalHuman.infra.llm.ModelPlatform;
import com.sugarweb.digitalHuman.infra.llm.ModelType;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.oss.application.dto.FileDetailDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Component
public class AutoInit implements ApplicationRunner {

    private final DatasetService datasetService;
    private final ActorService actorService;
    private final ScriptService scriptService;
    private final DocumentService documentService;
    private final FileService fileService;
    private final ModelService modelService;

    public AutoInit(DatasetService datasetService, ActorService actorService, ScriptService scriptService, DocumentService documentService, FileService fileService, ModelService modelService) {
        this.datasetService = datasetService;
        this.actorService = actorService;
        this.scriptService = scriptService;
        this.documentService = documentService;
        this.fileService = fileService;
        this.modelService = modelService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // defaultChatModelInfo();
        //
        // defaultEmbeddingModelInfo();
        //
        // defaultKbInfo();
        //
        // defaultActorInfo();

    }

    private final String defaultChatModelId = "1";
    private final String defaultEmbeddingModelId = "1";
    private final String defaultSystemPromptId = "1";
    private final String defaultDatasetId = "1";
    private final String defaultStageId = "1";
    private final String defaultActorId = "1";

    public Model defaultChatModelInfo() {
        Model model = new Model();
        model.setModelId(defaultChatModelId);
        model.setModelType(ModelType.CHAT.getValue());
        model.setModelPlatform(ModelPlatform.OLLAMA.getValue());
        model.setModelName("qwen2.5:3b");
        model.setBaseUrl("http://localhost:11434");
        Db.saveOrUpdate(model);
        return model;
    }

    public Model defaultEmbeddingModelInfo() {
        Model model = new Model();
        model.setModelId(defaultEmbeddingModelId);
        model.setModelType(ModelType.EMBEDDING.getValue());
        model.setModelPlatform(ModelPlatform.OLLAMA.getValue());
        model.setModelName("nomic-embed-text");
        model.setBaseUrl("http://localhost:11434");
        Db.saveOrUpdate(model);
        return model;
    }

    public DatasetDetailDto defaultKbInfo() {
        DatasetSaveDto datasetSaveDto = new DatasetSaveDto();
        datasetSaveDto.setDatasetName("test");
        datasetSaveDto.setEmbeddingModelId(defaultEmbeddingModelId);
        datasetSaveDto.setDescription("这是一个测试知识库");
        DatasetDetailDto save = datasetService.save(datasetSaveDto);
        return save;
    }

    public DocumentDetailDto step4(DatasetDetailDto datasetDetailDto) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("D:\\test.txt");
        FileDetailDto docFile = fileService.upload("doc_file", fileInputStream, "text/plain", "test.txt");

        DatasetDocument datasetDocument = new DatasetDocument();
        datasetDocument.setDocumentName(docFile.getFilename());
        datasetDocument.setDatasetId(datasetDetailDto.getDatasetId());
        datasetDocument.setSourceType(DocSourceType.FILE_UPLOAD.getValue());

        DocumentSaveDto saveDto = new DocumentSaveDto();
        saveDto.setDocumentName(datasetDocument.getDocumentName());
        saveDto.setDatasetId(datasetDocument.getDatasetId());
        saveDto.setSourceType(datasetDocument.getSourceType());
        DocumentDetailDto save = documentService.save(saveDto);
        documentService.parseStart(save.getDatasetId(), Collections.singletonList(save.getDocumentId()));
        return save;
    }

    public Actor defaultActorInfo() {
        Actor actor = new Actor();
        actor.setActorId("1");
        actor.setActorName("炫妹");
        actor.setChatModelId(defaultChatModelId);
        actor.setDatasetId(defaultDatasetId);
        Db.saveOrUpdate(actor);
        return actor;
    }


    public Stage defaultStage() {
        Stage stage = new Stage();
        stage.setStageId(defaultStageId);
        stage.setStageName("哔哩哔哩直播");
        stage.setDescription("哔哩哔哩直播");
        Db.saveOrUpdate(stage);
        return stage;
    }

}
