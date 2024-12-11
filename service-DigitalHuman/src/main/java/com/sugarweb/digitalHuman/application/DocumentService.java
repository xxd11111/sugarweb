package com.sugarweb.digitalHuman.application;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.DocumentDetailDto;
import com.sugarweb.digitalHuman.application.dto.DocumentPageQuery;
import com.sugarweb.digitalHuman.application.dto.DocumentSaveDto;
import com.sugarweb.digitalHuman.application.dto.DocumentUpdateDto;
import com.sugarweb.digitalHuman.common.DocParseStatus;
import com.sugarweb.digitalHuman.common.DocSourceType;
import com.sugarweb.digitalHuman.domain.DatasetDocument;
import com.sugarweb.digitalHuman.domain.DatasetDocumentSegment;
import com.sugarweb.digitalHuman.domain.Dataset;
import com.sugarweb.digitalHuman.domain.Model;
import com.sugarweb.digitalHuman.infra.MilvusEmbeddingStoreFactory;
import com.sugarweb.digitalHuman.infra.llm.ModelFactory;
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


    public DatasetDocument getDocInfo(String docId) {
        DatasetDocument datasetDocument = Db.getById(docId, DatasetDocument.class);
        if (datasetDocument == null) {
            return null;
        }
        if (DocSourceType.FILE_UPLOAD.getValue().equals(datasetDocument.getSourceType())) {
            FileInfo fileInfo = fileLinkService.getFileInfoByGroup(docId, "doc_file");
            datasetDocument.setFileInfo(fileInfo);
        }
        return datasetDocument;
    }

    public IPage<DocumentDetailDto> page(DocumentPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<>(DatasetDocument.class)
                .eq(DatasetDocument::getDocumentName, query.getDocumentName())
        ).convert(this::buildDocDetailDto);
    }

    public DocumentDetailDto detail(String docId) {
        DatasetDocument datasetDocument = getDocInfo(docId);
        return buildDocDetailDto(datasetDocument);
    }

    public DocumentDetailDto save(DocumentSaveDto saveDto) {
        DatasetDocument datasetDocument = new DatasetDocument();
        datasetDocument.setDatasetId(saveDto.getDatasetId());
        datasetDocument.setDocumentName(saveDto.getDocumentName());
        datasetDocument.setSourceType(saveDto.getSourceType());
        datasetDocument.setParseStatus(DocParseStatus.NOT_PARSED.getValue());
        return buildDocDetailDto(datasetDocument);
    }

    public DocumentDetailDto update(DocumentUpdateDto updateDto) {
        DatasetDocument datasetDocument = Db.getById(updateDto.getDocumentId(), DatasetDocument.class);
        if (datasetDocument == null) {
            return null;
        }
        datasetDocument.setDocumentName(updateDto.getDocumentName());
        Db.updateById(datasetDocument);
        return buildDocDetailDto(datasetDocument);
    }

    private DocumentDetailDto buildDocDetailDto(DatasetDocument datasetDocument) {
        if (datasetDocument == null) {
            return null;
        }
        DocumentDetailDto documentDetailDto = new DocumentDetailDto();
        BeanUtil.copyProperties(datasetDocument, documentDetailDto);
        return documentDetailDto;
    }

    public void remove(List<String> docIds) {
        for (String docId : docIds) {
            DatasetDocument datasetDocument = Db.getById(docId, DatasetDocument.class);
            Dataset dataset = Db.getById(datasetDocument.getDatasetId(), Dataset.class);
            MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(dataset);
            List<DatasetDocumentSegment> datasetDocumentSegmentList = Db.lambdaQuery(DatasetDocumentSegment.class).eq(DatasetDocumentSegment::getDocumentId, docId)
                    .list();
            if (CollUtil.isNotEmpty(datasetDocumentSegmentList)) {
                List<String> vectorIds = new ArrayList<>();
                for (DatasetDocumentSegment datasetDocumentSegment : datasetDocumentSegmentList) {
                    vectorIds.add(datasetDocumentSegment.getVectorId());
                }
                milvusEmbeddingStore.removeAll(vectorIds);
            }
            Db.removeById(docId, DatasetDocument.class);
        }
    }

    public void parseStart(String datasetId, List<String> docIds) {
        Dataset dataset = Db.getById(datasetId, Dataset.class);
        String modelId = dataset.getEmbeddingModelId();

        Model model = Db.getById(modelId, Model.class);
        EmbeddingModel embeddingModel = ModelFactory.creatEmbeddingModel(model);
        MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(dataset);

        for (String docId : docIds) {
            DatasetDocument datasetDocument = getDocInfo(docId);
            try {
                if (DocSourceType.FILE_UPLOAD.getValue().equals(datasetDocument.getSourceType())) {
                    FileInfo fileInfo = datasetDocument.getFileInfo();
                    if (fileInfo == null) {
                        throw new IllegalArgumentException("文件不存在");
                    }
                    InputStream fileInputStream = fileService.getContentByKey(fileInfo.getFileKey());
                    Document document;
                    if (StrUtil.equalsAny(fileInfo.getFileType(), "doc", "docx")) {
                        // docx效果好，xlsx效果接近，ppt效果好
                        document = new ApachePoiDocumentParser().parse(fileInputStream);
                    } else if (StrUtil.equalsAny(fileInfo.getFileType(), "xls", "xlsx")) {
                        // docx效果好，xlsx效果接近，ppt效果好
                        document = new ApachePoiDocumentParser().parse(fileInputStream);
                    } else if (StrUtil.equalsAny(fileInfo.getFileType(), "ppt", "pptx")) {
                        // docx效果好，xlsx效果接近，ppt效果好
                        document = new ApachePoiDocumentParser().parse(fileInputStream);
                    } else if (StrUtil.equalsAny(fileInfo.getFileType(), "pdf")) {
                        // pdf无空格行
                        document = new ApachePdfBoxDocumentParser().parse(fileInputStream);
                    } else if (StrUtil.equalsAny(fileInfo.getFileType(), "txt", "md")) {
                        document = new TextDocumentParser().parse(fileInputStream);
                    } else {
                        throw new IllegalArgumentException("文件解析，不支持的文件类型");
                    }
                    DocumentSplitter recursive = DocumentSplitters.recursive(500, 50);
                    document.metadata().put("docId", datasetDocument.getDocumentId());
                    document.metadata().put("datasetId", datasetDocument.getDatasetId());
                    List<TextSegment> textSegmentList = recursive.split(document);
                    Response<List<Embedding>> listResponse = embeddingModel.embedAll(textSegmentList);
                    List<Embedding> content = listResponse.content();
                    List<String> vectorIds = milvusEmbeddingStore.addAll(content, textSegmentList);
                    List<DatasetDocumentSegment> datasetDocumentSegment = buildDocSegment(textSegmentList, vectorIds, datasetId, docId);
                    Db.saveBatch(datasetDocumentSegment);

                    datasetDocument.setParseStatus(DocParseStatus.PARSED.getValue());
                    datasetDocument.setErrorMsg("");
                    datasetDocument.setSegmentCount(CollUtil.size(datasetDocumentSegment));
                    Db.updateById(datasetDocument);

                } else if (DocSourceType.MANUAL_INPUT.getValue().equals(datasetDocument.getSourceType())) {
                    List<DatasetDocumentSegment> datasetDocumentSegmentList = Db.lambdaQuery(DatasetDocumentSegment.class)
                            .eq(DatasetDocumentSegment::getDocumentId, docId)
                            .list();
                    Metadata metadata = new Metadata();
                    metadata.put("docId", datasetDocument.getDocumentId());
                    metadata.put("datasetId", datasetDocument.getDatasetId());
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
                datasetDocument.setParseStatus(DocParseStatus.FAILED.getValue());
                datasetDocument.setErrorMsg(e.getMessage());
                Db.updateById(datasetDocument);
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
