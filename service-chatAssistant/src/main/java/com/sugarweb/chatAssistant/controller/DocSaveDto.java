package com.sugarweb.chatAssistant.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.oss.domain.po.FileInfo;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
public class DocSaveDto {

    private String kbId;

    private String docName;

    private FileInfo fileInfo;

    private String docType;

    private String docSize;

    // 手动创建，文件上传
    private String sourceType;

}
