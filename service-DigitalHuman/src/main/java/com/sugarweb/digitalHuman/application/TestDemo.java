package com.sugarweb.digitalHuman.application;

import com.sugarweb.digitalHuman.application.dto.*;
import com.sugarweb.digitalHuman.constants.DocSourceType;
import com.sugarweb.digitalHuman.domain.DatasetDocument;
import com.sugarweb.digitalHuman.domain.Model;
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
    private final DatasetService datasetService;
    private final FileService fileService;
    private final DocumentService documentService;
    private final ActorService actorService;

    public TestDemo(ModelService modelService, DatasetService datasetService, FileService fileService, DocumentService documentService, ActorService actorService) {
        this.modelService = modelService;
        this.datasetService = datasetService;
        this.fileService = fileService;
        this.documentService = documentService;
        this.actorService = actorService;
    }

    // step1 配置问答模型
    public Model step1() {
        Model model = new Model();
        model.setModelType(ModelType.CHAT.getValue());
        model.setModelPlatform(ModelPlatform.OLLAMA.getValue());
        model.setModelName("qwen2.5:3b");
        model.setBaseUrl("http://localhost:11434");
        modelService.save(model);
        return model;
    }

    // step2 配置向量模型
    public Model step2() {
        Model model = new Model();
        model.setModelType(ModelType.EMBEDDING.getValue());
        model.setModelPlatform(ModelPlatform.OLLAMA.getValue());
        model.setModelName("nomic-embed-text");
        model.setBaseUrl("http://localhost:11434");
        modelService.save(model);
        return model;
    }

    public DatasetDetailDto step3(Model model) {
        DatasetSaveDto datasetSaveDto = new DatasetSaveDto();
        datasetSaveDto.setDatasetName("test");
        datasetSaveDto.setEmbeddingModelId(model.getModelId());
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

    public void step5(Model chatModel) {
        ActorSaveDto saveDto = new ActorSaveDto();
        saveDto.setActorName("test_actor");
        saveDto.setChatModelId(chatModel.getModelId());
        actorService.save(saveDto);
    }


}
