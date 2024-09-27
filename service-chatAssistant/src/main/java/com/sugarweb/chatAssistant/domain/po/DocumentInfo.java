package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Document
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class DocumentInfo {

    @TableId
    private String documentId;

    private String documentName;

    private String documentPath;

    private String documentType;

    private String documentSize;

    private String createTime;

    private String updateTime;

}
