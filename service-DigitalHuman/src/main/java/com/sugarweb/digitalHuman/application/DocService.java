package com.sugarweb.digitalHuman.application;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.DocDetailDto;
import com.sugarweb.digitalHuman.application.dto.DocPageQuery;
import com.sugarweb.digitalHuman.application.dto.DocSaveDto;
import com.sugarweb.digitalHuman.application.dto.DocUpdateDto;
import com.sugarweb.digitalHuman.domain.DocInfo;
import com.sugarweb.digitalHuman.domain.KbInfo;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.digitalHuman.infra.llm.ModelFactory;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.oss.domain.po.FileInfo;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
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
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DocService
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class DocService {

    @Resource
    private FileService fileService;

    @Resource
    private MilvusEmbeddingStore milvusEmbeddingStore;

    public IPage<DocDetailDto> page(DocPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<>(DocInfo.class)
                .eq(DocInfo::getDocName, query.getDocName())
        ).convert(this::buildDocDetailDto);
    }

    public DocDetailDto detail(String docId) {
        DocInfo docInfo = Db.getById(docId, DocInfo.class);
        return buildDocDetailDto(docInfo);
    }

    public DocDetailDto save(DocSaveDto saveDto) {
        DocInfo docInfo = new DocInfo();
        docInfo.setKbId(saveDto.getKbId());
        docInfo.setDocName(saveDto.getDocName());
        docInfo.setSourceType(saveDto.getSourceType());
        docInfo.setDocStatus(saveDto.getDocStatus());
        docInfo.setParseStatus("1");
        docInfo.setCreateTime(LocalDateTime.now());
        docInfo.setUpdateTime(LocalDateTime.now());
        return buildDocDetailDto(docInfo);
    }

    public DocDetailDto update(DocUpdateDto updateDto) {
        DocInfo docInfo = Db.getById(updateDto.getDocId(), DocInfo.class);
        if (docInfo == null) {
            return null;
        }
        docInfo.setDocName(updateDto.getDocName());
        docInfo.setUpdateTime(LocalDateTime.now());
        Db.updateById(docInfo);
        return buildDocDetailDto(docInfo);
    }

    private DocDetailDto buildDocDetailDto(DocInfo docInfo) {
        if (docInfo == null) {
            return null;
        }
        DocDetailDto docDetailDto = new DocDetailDto();
        BeanUtil.copyProperties(docInfo, docDetailDto);
        return docDetailDto;
    }

    public void remove(String docId) {
        Db.removeById(docId, DocInfo.class);
    }

    public void parseStart(String kbId, List<String> docIds) {
        KbInfo kbInfo = Db.getById(kbId, KbInfo.class);
        String modelId = kbInfo.getEmbeddingModelId();

        ModelInfo modelInfo = Db.getById(modelId, ModelInfo.class);
        EmbeddingModel embeddingModel = ModelFactory.creatEmbeddingModel(modelInfo);

        for (String docId : docIds) {
            DocInfo docInfo = Db.getById(docId, DocInfo.class);
            FileInfo fileInfo = docInfo.getFileInfo();
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
            document.metadata().put("docId", docId);
            document.metadata().put("kbId", docInfo.getKbId());
            List<TextSegment> textSegmentList = recursive.split(document);
            Response<List<Embedding>> listResponse = embeddingModel.embedAll(textSegmentList);
            List<Embedding> content = listResponse.content();
            milvusEmbeddingStore.addAll(content, textSegmentList);
        }

    }

}
