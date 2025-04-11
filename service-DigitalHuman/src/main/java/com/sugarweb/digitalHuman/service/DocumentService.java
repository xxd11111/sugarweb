package com.sugarweb.digitalHuman.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.service.dto.DocumentDetailDto;
import com.sugarweb.digitalHuman.service.dto.DocumentPageQuery;
import com.sugarweb.digitalHuman.service.dto.DocumentSaveDto;
import com.sugarweb.digitalHuman.service.dto.DocumentUpdateDto;
import com.sugarweb.digitalHuman.common.DocParseStatus;
import com.sugarweb.digitalHuman.common.DocSourceType;
import com.sugarweb.digitalHuman.entity.KbDocument;
import com.sugarweb.digitalHuman.entity.DatasetDocumentSegment;
import com.sugarweb.digitalHuman.entity.Kb;
import com.sugarweb.digitalHuman.entity.Model;
import com.sugarweb.digitalHuman.component.MilvusEmbeddingStoreFactory;
import com.sugarweb.digitalHuman.component.llm.ModelFactory;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.oss.application.FileLinkService;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.oss.domain.po.FileInfo;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * DocService
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class DocumentService {

    @Resource
    private FileService fileService;
    @Autowired
    private FileLinkService fileLinkService;


    public KbDocument getDocInfo(String docId) {
        KbDocument kbDocument = Db.getById(docId, KbDocument.class);
        if (kbDocument == null) {
            return null;
        }
        if (DocSourceType.FILE_UPLOAD.getValue().equals(kbDocument.getSourceType())) {
            FileInfo fileInfo = fileLinkService.getFileInfoByGroup(docId, "doc_file");
            kbDocument.setFileInfo(fileInfo);
        }
        return kbDocument;
    }

    public IPage<DocumentDetailDto> page(DocumentPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<>(KbDocument.class)
                .eq(KbDocument::getDocumentName, query.getDocumentName())
        ).convert(this::buildDocDetailDto);
    }

    public DocumentDetailDto detail(String docId) {
        KbDocument kbDocument = getDocInfo(docId);
        return buildDocDetailDto(kbDocument);
    }

    public DocumentDetailDto save(DocumentSaveDto saveDto) {
        KbDocument kbDocument = new KbDocument();
        kbDocument.setDatasetId(saveDto.getDatasetId());
        kbDocument.setDocumentName(saveDto.getDocumentName());
        kbDocument.setSourceType(saveDto.getSourceType());
        kbDocument.setParseStatus(DocParseStatus.NOT_PARSED.getValue());
        return buildDocDetailDto(kbDocument);
    }

    public DocumentDetailDto update(DocumentUpdateDto updateDto) {
        KbDocument kbDocument = Db.getById(updateDto.getDocumentId(), KbDocument.class);
        if (kbDocument == null) {
            return null;
        }
        kbDocument.setDocumentName(updateDto.getDocumentName());
        Db.updateById(kbDocument);
        return buildDocDetailDto(kbDocument);
    }

    private DocumentDetailDto buildDocDetailDto(KbDocument kbDocument) {
        if (kbDocument == null) {
            return null;
        }
        DocumentDetailDto documentDetailDto = new DocumentDetailDto();
        BeanUtil.copyProperties(kbDocument, documentDetailDto);
        return documentDetailDto;
    }

    public void remove(List<String> docIds) {
        for (String docId : docIds) {
            KbDocument kbDocument = Db.getById(docId, KbDocument.class);
            Kb kb = Db.getById(kbDocument.getDatasetId(), Kb.class);
            MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(kb);
            List<DatasetDocumentSegment> datasetDocumentSegmentList = Db.lambdaQuery(DatasetDocumentSegment.class).eq(DatasetDocumentSegment::getDocumentId, docId)
                    .list();
            if (CollUtil.isNotEmpty(datasetDocumentSegmentList)) {
                List<String> vectorIds = new ArrayList<>();
                for (DatasetDocumentSegment datasetDocumentSegment : datasetDocumentSegmentList) {
                    vectorIds.add(datasetDocumentSegment.getVectorId());
                }
                milvusEmbeddingStore.removeAll(vectorIds);
            }
            Db.removeById(docId, KbDocument.class);
        }
    }

    public void parseStart(String datasetId, List<String> docIds) {
        Kb kb = Db.getById(datasetId, Kb.class);
        String modelId = kb.getEmbeddingModelId();

        Model model = Db.getById(modelId, Model.class);
        EmbeddingModel embeddingModel = ModelFactory.creatEmbeddingModel(model);
        MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(kb);

        for (String docId : docIds) {
            KbDocument kbDocument = getDocInfo(docId);
            try {
                if (DocSourceType.FILE_UPLOAD.getValue().equals(kbDocument.getSourceType())) {
                    FileInfo fileInfo = kbDocument.getFileInfo();
                    if (fileInfo == null) {
                        throw new IllegalArgumentException("文件不存在");
                    }
                    InputStream fileInputStream = fileService.getContentByKey(fileInfo.getFileKey());
                    Document document;
                    if (StrUtil.equalsAny(fileInfo.getFileSuffix(), "doc", "docx")) {
                        // docx效果好，xlsx效果接近，ppt效果好
                        document = new ApachePoiDocumentParser().parse(fileInputStream);
                    } else if (StrUtil.equalsAny(fileInfo.getFileSuffix(), "xls", "xlsx")) {
                        // docx效果好，xlsx效果接近，ppt效果好
                        document = new ApachePoiDocumentParser().parse(fileInputStream);
                    } else if (StrUtil.equalsAny(fileInfo.getFileSuffix(), "ppt", "pptx")) {
                        // docx效果好，xlsx效果接近，ppt效果好
                        document = new ApachePoiDocumentParser().parse(fileInputStream);
                    } else if (StrUtil.equalsAny(fileInfo.getFileSuffix(), "pdf")) {
                        // pdf无空格行
                        document = new ApachePdfBoxDocumentParser().parse(fileInputStream);
                    } else if (StrUtil.equalsAny(fileInfo.getFileSuffix(), "txt", "md")) {
                        document = new TextDocumentParser().parse(fileInputStream);
                    } else {
                        throw new IllegalArgumentException("文件解析，不支持的文件类型");
                    }
                    DocumentSplitter recursive = DocumentSplitters.recursive(500, 50);
                    document.metadata().put("docId", kbDocument.getDocumentId());
                    document.metadata().put("datasetId", kbDocument.getDatasetId());
                    List<TextSegment> textSegmentList = recursive.split(document);
                    Response<List<Embedding>> listResponse = embeddingModel.embedAll(textSegmentList);
                    List<Embedding> content = listResponse.content();
                    List<String> vectorIds = milvusEmbeddingStore.addAll(content, textSegmentList);
                    List<DatasetDocumentSegment> datasetDocumentSegment = buildDocSegment(textSegmentList, vectorIds, datasetId, docId);
                    Db.saveBatch(datasetDocumentSegment);

                    kbDocument.setParseStatus(DocParseStatus.PARSED.getValue());
                    kbDocument.setErrorMsg("");
                    kbDocument.setSegmentCount(CollUtil.size(datasetDocumentSegment));
                    Db.updateById(kbDocument);

                } else if (DocSourceType.MANUAL_INPUT.getValue().equals(kbDocument.getSourceType())) {
                    List<DatasetDocumentSegment> datasetDocumentSegmentList = Db.lambdaQuery(DatasetDocumentSegment.class)
                            .eq(DatasetDocumentSegment::getDocumentId, docId)
                            .list();
                    Metadata metadata = new Metadata();
                    metadata.put("docId", kbDocument.getDocumentId());
                    metadata.put("datasetId", kbDocument.getDatasetId());
                    List<TextSegment> textSegmentList = datasetDocumentSegmentList.stream().map(a -> TextSegment.textSegment(a.getContent(), metadata)).toList();
                    Response<List<Embedding>> listResponse = embeddingModel.embedAll(textSegmentList);
                    List<String> vectorIds = milvusEmbeddingStore.addAll(listResponse.content(), textSegmentList);

                    if (CollUtil.size(textSegmentList) != CollUtil.size(vectorIds)) {
                        throw new IllegalArgumentException("textSegmentList和vectorIds长度不一致");
                    }
                    for (int i = 0; i < datasetDocumentSegmentList.size(); i++) {
                        datasetDocumentSegmentList.get(i).setVectorId(vectorIds.get(i));
                    }
                    Db.updateBatchById(datasetDocumentSegmentList);
                } else {
                    throw new IllegalArgumentException("不支持的文档类型");
                }
            } catch (Exception e) {
                kbDocument.setParseStatus(DocParseStatus.FAILED.getValue());
                kbDocument.setErrorMsg(e.getMessage());
                Db.updateById(kbDocument);
            }
        }
    }

    private List<DatasetDocumentSegment> buildDocSegment(List<TextSegment> textSegmentList, List<String> vectorIds, String datasetId, String docId) {
        if (CollUtil.size(textSegmentList) != CollUtil.size(vectorIds)) {
            throw new IllegalArgumentException("textSegmentList和vectorIds长度不一致");
        }
        List<DatasetDocumentSegment> datasetDocumentSegmentList = new ArrayList<>();
        for (int i = 0; i < textSegmentList.size(); i++) {
            DatasetDocumentSegment datasetDocumentSegment = new DatasetDocumentSegment();
            datasetDocumentSegment.setDatasetId(datasetId);
            datasetDocumentSegment.setDocumentId(docId);
            datasetDocumentSegment.setVectorId(vectorIds.get(i));
            datasetDocumentSegment.setContent(textSegmentList.get(i).text());
            datasetDocumentSegment.setPosition(i);
            datasetDocumentSegmentList.add(datasetDocumentSegment);
        }
        return datasetDocumentSegmentList;
    }

}
