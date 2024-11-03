package com.sugarweb.chatAssistant.application;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.DocInfo;
import com.sugarweb.oss.domain.po.FileInfo;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * ParseManager
 *
 * @author xxd
 * @since 2024/11/1 21:36
 */
public class ParseManager {

    public void start(String docId) {
        DocInfo docInfo = Db.getById(docId, DocInfo.class);
        FileInfo fileInfo = docInfo.getFileInfo();
        if ("".equals(fileInfo.getFileType())) {

        } else if ("pdf".equals(fileInfo.getFileType())) {

        }

    }

    public void stop(String docId) {


    }

    public static void main(String[] args) throws FileNotFoundException {
        // String fileName = "C:\\xxd-work\\java-project\\sugarcoat\\service-chatAssistant\\src\\main\\resources\\doc\\blank-file.txt";
        // String fileName = "C:\\xxd-work\\java-project\\sugarcoat\\service-chatAssistant\\src\\main\\resources\\doc\\test-file.txt";
        // String fileName = "C:\\Users\\xxd\\Desktop\\xxd\\SS类岗位任职资格管理办法及附件\\思创股份SS类岗位任职资格管理办法-修订20231211.docx";
        // String fileName = "C:\\Users\\xxd\\Desktop\\xxd\\SS类岗位任职资格管理办法及附件\\许向东：SS类岗位任职资格认证申请表.xlsx";
        String fileName = "C:\\Users\\xxd\\Desktop\\xxd\\xr文档.pptx";

        // String fileName = "C:\\Users\\xxd\\Desktop\\xxd\\思创股份SS类岗位任职资格管理办法-修订20231211.pdf";

        File file = new File(fileName);

        // docx 解析一坨  xlsx 效果接近， ppt一坨， pdf 有空格行
        Document document = new ApacheTikaDocumentParser().parse(new FileInputStream(file));
        System.out.println(document);

        // docx效果好，xlsx效果接近，ppt效果好
        Document parse1 = new ApachePoiDocumentParser().parse(new FileInputStream(file));
        System.out.println(parse1);

        // pdf无空格行
        Document parse2 = new ApachePdfBoxDocumentParser().parse(new FileInputStream(file));
        System.out.println(parse2);


        //总结 doc/docx  选ApachePoiDocumentParser, pdf选ApachePdfBoxDocumentParser; xlsx/xls使用easyExcel
        //需要自己写个excel 根据header解析 EasyExcel;
    }

}
