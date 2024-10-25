package com.sugarweb.chatAssistant.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.oss.domain.po.FileInfo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Document
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DocInfo {

    @TableId
    private String docId;

    private String docName;

    @TableField(exist = false)
    private FileInfo docFile;

    private String docType;

    private String docSize;

    // 手动创建，文件上传
    private String sourceType;

    private String docStatus;

    private String parseStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
