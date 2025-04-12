package com.sugarweb.digitalHuman.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.service.dto.KbDocDetailDto;
import com.sugarweb.digitalHuman.service.dto.KbDocPageQuery;
import com.sugarweb.digitalHuman.service.dto.DocSaveDto;
import com.sugarweb.digitalHuman.service.dto.DocRenameDto;
import com.sugarweb.digitalHuman.common.DocParseStatus;
import com.sugarweb.digitalHuman.common.DocSourceType;
import com.sugarweb.digitalHuman.domain.KbDoc;
import com.sugarweb.digitalHuman.domain.KbDocSegment;
import com.sugarweb.digitalHuman.domain.Kb;
import com.sugarweb.digitalHuman.domain.Model;
import com.sugarweb.digitalHuman.component.MilvusEmbeddingStoreFactory;
import com.sugarweb.digitalHuman.component.model.ModelFactory;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.oss.service.FileLinkService;
import com.sugarweb.oss.service.FileService;
import com.sugarweb.oss.domain.FileInfo;
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
public class KbDocService {

    @Resource
    private FileService fileService;
    @Autowired
    private FileLinkService fileLinkService;

    public KbDoc getDocInfo(String docId) {
        KbDoc kbDoc = Db.getById(docId, KbDoc.class);
        if (kbDoc == null) {
            return null;
        }
        if (DocSourceType.FILE_UPLOAD.getValue().equals(kbDoc.getSourceType())) {
            FileInfo fileInfo = fileLinkService.getFileInfoByGroup(docId, "doc_file");
            kbDoc.setFileInfo(fileInfo);
        }
        return kbDoc;
    }

    public IPage<KbDocDetailDto> page(KbDocPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<>(KbDoc.class)
                .eq(KbDoc::getDocName, query.getDocumentName())
        ).convert(this::buildDocDetailDto);
    }

    public KbDocDetailDto detail(String docId) {
        KbDoc kbDoc = getDocInfo(docId);
        return buildDocDetailDto(kbDoc);
    }

    public KbDocDetailDto save(DocSaveDto saveDto) {
        KbDoc kbDoc = new KbDoc();
        kbDoc.setKbId(saveDto.getDatasetId());
        kbDoc.setDocName(saveDto.getDocumentName());
        kbDoc.setSourceType(saveDto.getSourceType());
        kbDoc.setParseStatus(DocParseStatus.NOT_PARSED.getValue());
        return buildDocDetailDto(kbDoc);
    }

    public KbDocDetailDto update(DocRenameDto updateDto) {
        KbDoc kbDoc = Db.getById(updateDto.getDocId(), KbDoc.class);
        if (kbDoc == null) {
            return null;
        }
        kbDoc.setDocName(updateDto.getDocName());
        Db.updateById(kbDoc);
        return buildDocDetailDto(kbDoc);
    }

    private KbDocDetailDto buildDocDetailDto(KbDoc kbDoc) {
        if (kbDoc == null) {
            return null;
        }
        KbDocDetailDto kbDocDetailDto = new KbDocDetailDto();
        BeanUtil.copyProperties(kbDoc, kbDocDetailDto);
        return kbDocDetailDto;
    }

    public void remove(List<String> docIds) {
        for (String docId : docIds) {
            KbDoc kbDoc = Db.getById(docId, KbDoc.class);
            Kb kb = Db.getById(kbDoc.getKbId(), Kb.class);
            MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(kb);
            List<KbDocSegment> kbDocSegmentList = Db.lambdaQuery(KbDocSegment.class).eq(KbDocSegment::getDocId, docId)
                    .list();
            if (CollUtil.isNotEmpty(kbDocSegmentList)) {
                List<String> vectorIds = new ArrayList<>();
                for (KbDocSegment kbDocSegment : kbDocSegmentList) {
                    vectorIds.add(kbDocSegment.getVectorId());
                }
                milvusEmbeddingStore.removeAll(vectorIds);
            }
            Db.removeById(docId, KbDoc.class);
        }
    }

    public void parseStart(String datasetId, List<String> docIds) {
        Kb kb = Db.getById(datasetId, Kb.class);
        String modelId = kb.getEmbeddingModelId();

        Model model = Db.getById(modelId, Model.class);
        EmbeddingModel embeddingModel = ModelFactory.creatEmbeddingModel(model);
        MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(kb);

        for (String docId : docIds) {
            KbDoc kbDoc = getDocInfo(docId);
            try {
                if (DocSourceType.FILE_UPLOAD.getValue().equals(kbDoc.getSourceType())) {
                    FileInfo fileInfo = kbDoc.getFileInfo();
                    if (fileInfo == null) {
                        throw new ValidateException("文件不存在");
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
                        throw new ValidateException("文件解析，不支持的文件类型");
                    }
                    DocumentSplitter recursive = DocumentSplitters.recursive(500, 50);
                    document.metadata().put("docId", kbDoc.getDocId());
                    document.metadata().put("datasetId", kbDoc.getKbId());
                    List<TextSegment> textSegmentList = recursive.split(document);
                    Response<List<Embedding>> listResponse = embeddingModel.embedAll(textSegmentList);
                    List<Embedding> content = listResponse.content();
                    List<String> vectorIds = milvusEmbeddingStore.addAll(content, textSegmentList);
                    List<KbDocSegment> kbDocSegment = buildDocSegment(textSegmentList, vectorIds, datasetId, docId);
                    Db.saveBatch(kbDocSegment);

                    kbDoc.setParseStatus(DocParseStatus.PARSED.getValue());
                    kbDoc.setParseMsg("");
                    kbDoc.setSegmentCount(CollUtil.size(kbDocSegment));
                    Db.updateById(kbDoc);

                } else if (DocSourceType.MANUAL_INPUT.getValue().equals(kbDoc.getSourceType())) {
                    List<KbDocSegment> kbDocSegmentList = Db.lambdaQuery(KbDocSegment.class)
                            .eq(KbDocSegment::getDocId, docId)
                            .list();
                    Metadata metadata = new Metadata();
                    metadata.put("docId", kbDoc.getDocId());
                    metadata.put("datasetId", kbDoc.getKbId());
                    List<TextSegment> textSegmentList = kbDocSegmentList.stream().map(a -> TextSegment.textSegment(a.getContent(), metadata)).toList();
                    Response<List<Embedding>> listResponse = embeddingModel.embedAll(textSegmentList);
                    List<String> vectorIds = milvusEmbeddingStore.addAll(listResponse.content(), textSegmentList);

                    if (CollUtil.size(textSegmentList) != CollUtil.size(vectorIds)) {
                        throw new ValidateException("textSegmentList和vectorIds长度不一致");
                    }
                    for (int i = 0; i < kbDocSegmentList.size(); i++) {
                        kbDocSegmentList.get(i).setVectorId(vectorIds.get(i));
                    }
                    Db.updateBatchById(kbDocSegmentList);
                } else {
                    throw new ValidateException("不支持的文档类型");
                }
            } catch (Exception e) {
                kbDoc.setParseStatus(DocParseStatus.FAILED.getValue());
                kbDoc.setParseMsg(e.getMessage());
                Db.updateById(kbDoc);
            }
        }
    }

    private List<KbDocSegment> buildDocSegment(List<TextSegment> textSegmentList, List<String> vectorIds, String datasetId, String docId) {
        if (CollUtil.size(textSegmentList) != CollUtil.size(vectorIds)) {
            throw new ValidateException("textSegmentList和vectorIds长度不一致");
        }
        List<KbDocSegment> kbDocSegmentList = new ArrayList<>();
        for (int i = 0; i < textSegmentList.size(); i++) {
            KbDocSegment kbDocSegment = new KbDocSegment();
            kbDocSegment.setKbId(datasetId);
            kbDocSegment.setDocId(docId);
            kbDocSegment.setVectorId(vectorIds.get(i));
            kbDocSegment.setContent(textSegmentList.get(i).text());
            kbDocSegmentList.add(kbDocSegment);
        }
        return kbDocSegmentList;
    }

}
